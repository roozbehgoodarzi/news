package com.upday.news.repository;

import com.upday.news.model.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void init(){
        articleRepository.deleteAll();
    }

    @Test
    void shouldPersistAndRetrieve() {
        var article = new Article();
        article.setHeader("header");
        article = articleRepository.save(article);
        Assertions.assertThat(article.getHeader().equals(articleRepository.findById(article.getId())));
        Assertions.assertThat(article.getId()).isNotNull();
    }
}