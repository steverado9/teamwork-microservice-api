package com.steverado.article_service.mappers;

import com.steverado.article_service.dto.CommentItemsDto;
import com.steverado.article_service.entity.ArticleComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentItemsMapper {

<<<<<<< HEAD
    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "userId", target = "authorId")
=======
    @Mapping(source = "comment_id", target = "commentId")
    @Mapping(source = "user.id", target = "authorId")
>>>>>>> fe30fbc2a1bd1887e71828b7b30ebf900ef75c16
    CommentItemsDto articleComment(ArticleComment comment);
}
