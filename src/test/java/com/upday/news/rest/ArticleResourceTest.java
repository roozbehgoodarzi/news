package com.upday.news.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.news.model.Article;
import com.upday.news.repository.ArticleRepository;
import com.upday.news.service.dto.ArticleDTO;
import com.upday.news.service.dto.CreateArticleDTO;
import com.upday.news.service.mapper.ArticleMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ArticleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArticleRepository repository;

    @Autowired
    ArticleMapper articleMapper;

    static CreateArticleDTO createArticleDTO = new CreateArticleDTO();

    public static final Instant PUBLISH_DATE = Instant.now();

    Article article = new Article();

    @BeforeAll
    public static void init(){
        createArticleDTO.setAuthors(List.of("author1","author2"));
        createArticleDTO.setDescription("description");
        createArticleDTO.setHeader("header1");
        createArticleDTO.setKeywords(List.of("keyword1", "keyword2", "keyword3"));
        createArticleDTO.setPublishDate(PUBLISH_DATE);
        createArticleDTO.setText("text");
    }

    @AfterEach
    void cleanup(){
       repository.deleteAll();
    }

    @AfterTestClass
    public void terminate(){
        repository.deleteAll();
    }

    @Test
    void createArticle() throws Exception {
       var result = mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createArticleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value(createArticleDTO.getDescription()))
                .andExpect(jsonPath("$.header").value(createArticleDTO.getHeader()))
                .andExpect(jsonPath("$.publishDate").isNotEmpty())
                .andExpect(jsonPath("$.text").value(createArticleDTO.getText()))
                .andExpect(jsonPath("$.authors.[0]").value(createArticleDTO.getAuthors().get(0)));
    }

    @Test
    void getArticleById() throws Exception {
        article = articleMapper.toEntity(createArticleDTO);
        var articleId = repository.save(article).getId();
        mockMvc.perform(get("/api/articles/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value(createArticleDTO.getDescription()))
                .andExpect(jsonPath("$.header").value(createArticleDTO.getHeader()))
                .andExpect(jsonPath("$.publishDate").isNotEmpty())
                .andExpect(jsonPath("$.text").value(createArticleDTO.getText()))
                .andExpect(jsonPath("$.authors.[0]").value(createArticleDTO.getAuthors().get(0)));
    }

    @Test
    void getArticleByFilter() throws Exception{
        article = articleMapper.toEntity(createArticleDTO);
        repository.save(article).getId();
        mockMvc.perform(get("/api/articles?publishDateFrom=" + PUBLISH_DATE.minusSeconds(10)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").isNotEmpty())
                .andExpect(jsonPath("$.[0]description").value(createArticleDTO.getDescription()))
                .andExpect(jsonPath("$.[0]header").value(createArticleDTO.getHeader()))
                .andExpect(jsonPath("$.[0]publishDate").isNotEmpty())
                .andExpect(jsonPath("$.[0]text").value(createArticleDTO.getText()))
                .andExpect(jsonPath("$.[0]authors.[0]").value(createArticleDTO.getAuthors().get(0)));
    }

    @Test
    void deleteArticle() throws Exception {
        article = articleMapper.toEntity(createArticleDTO);
        var articleId = repository.save(article).getId();
        mockMvc.perform(delete("/api/articles/{id}", articleId))
                .andExpect(status().isOk());
        Assertions.assertEquals(0, repository.findAll().size());
    }

    @Test
    void updateArticle() throws Exception{
        article = articleMapper.toEntity(createArticleDTO);
        var articleId = repository.save(article).getId();

        Article updateArticle = repository.findById(articleId).get();
        updateArticle.getAuthors().add("newAuthor");
        updateArticle.getKeywords().remove("keyword2");
        updateArticle.setDescription("new description");

        ArticleDTO articleDTO = articleMapper.toDto(updateArticle);

        mockMvc.perform(put("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(articleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value("new description"))
                .andExpect(jsonPath("$.header").value(createArticleDTO.getHeader()))
                .andExpect(jsonPath("$.publishDate").isNotEmpty())
                .andExpect(jsonPath("$.text").value(createArticleDTO.getText()))
                .andExpect(jsonPath("$.authors.[2]").value("newAuthor"))
                .andExpect(jsonPath("$.keywords.[1]").value("keyword3"));
    }

}