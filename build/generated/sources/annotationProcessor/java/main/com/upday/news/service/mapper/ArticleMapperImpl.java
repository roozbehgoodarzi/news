package com.upday.news.service.mapper;

import com.upday.news.model.Article;
import com.upday.news.service.dto.ArticleDTO;
import com.upday.news.service.dto.CreateArticleDTO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-10T16:04:33+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.0.2.jar, environment: Java 11.0.8 (AdoptOpenJDK)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(CreateArticleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Article article = new Article();

        article.setHeader( dto.getHeader() );
        article.setDescription( dto.getDescription() );
        article.setText( dto.getText() );
        article.setPublishDate( dto.getPublishDate() );
        List<String> list = dto.getAuthors();
        if ( list != null ) {
            article.setAuthors( new ArrayList<String>( list ) );
        }
        List<String> list1 = dto.getKeywords();
        if ( list1 != null ) {
            article.setKeywords( new ArrayList<String>( list1 ) );
        }

        return article;
    }

    @Override
    public Article toUpdateEntity(ArticleDTO articleDTO) {
        if ( articleDTO == null ) {
            return null;
        }

        Article article = new Article();

        article.setId( articleDTO.getId() );
        article.setHeader( articleDTO.getHeader() );
        article.setDescription( articleDTO.getDescription() );
        article.setText( articleDTO.getText() );
        if ( articleDTO.getPublishDate() != null ) {
            article.setPublishDate( Instant.parse( articleDTO.getPublishDate() ) );
        }
        List<String> list = articleDTO.getAuthors();
        if ( list != null ) {
            article.setAuthors( new ArrayList<String>( list ) );
        }
        List<String> list1 = articleDTO.getKeywords();
        if ( list1 != null ) {
            article.setKeywords( new ArrayList<String>( list1 ) );
        }

        return article;
    }

    @Override
    public ArticleDTO toDto(Article entity) {
        if ( entity == null ) {
            return null;
        }

        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setId( entity.getId() );
        articleDTO.setHeader( entity.getHeader() );
        articleDTO.setDescription( entity.getDescription() );
        articleDTO.setText( entity.getText() );
        if ( entity.getPublishDate() != null ) {
            articleDTO.setPublishDate( entity.getPublishDate().toString() );
        }
        List<String> list = entity.getAuthors();
        if ( list != null ) {
            articleDTO.setAuthors( new ArrayList<String>( list ) );
        }
        List<String> list1 = entity.getKeywords();
        if ( list1 != null ) {
            articleDTO.setKeywords( new ArrayList<String>( list1 ) );
        }

        return articleDTO;
    }
}
