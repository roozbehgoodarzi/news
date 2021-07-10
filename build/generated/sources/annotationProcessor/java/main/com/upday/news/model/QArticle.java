package com.upday.news.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -555970832L;

    public static final QArticle article = new QArticle("article");

    public final ListPath<String, StringPath> authors = this.<String, StringPath>createList("authors", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final StringPath header = createString("header");

    public final StringPath id = createString("id");

    public final ListPath<String, StringPath> keywords = this.<String, StringPath>createList("keywords", String.class, StringPath.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.Instant> publishDate = createDateTime("publishDate", java.time.Instant.class);

    public final StringPath text = createString("text");

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

