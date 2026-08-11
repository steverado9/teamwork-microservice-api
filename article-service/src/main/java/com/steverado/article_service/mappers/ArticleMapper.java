package com.steverado.article_service.mappers;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toEntity(ArticleDto articleDto);
}
