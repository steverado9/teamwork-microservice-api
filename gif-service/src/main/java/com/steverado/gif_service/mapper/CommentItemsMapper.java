package com.steverado.gif_service.mapper;

import com.steverado.gif_service.dto.CommentItemsDto;
import com.steverado.gif_service.entity.GifComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentItemsMapper {

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "userId", target = "authorId")
    CommentItemsDto gifComment(GifComment gifComment);
}
