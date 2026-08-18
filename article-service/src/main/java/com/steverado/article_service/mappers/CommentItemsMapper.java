package com.steverado.article_service.mappers;

import com.steverado.article_service.dto.CommentItemsDto;
import com.steverado.article_service.entity.ArticleComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentItemsMapper {

    @Mapping(source = "comment_id", target = "commentId")
    @Mapping(source = "user.id", target = "authorId")
    CommentItemsDto articleComment(ArticleComment comment);
}
