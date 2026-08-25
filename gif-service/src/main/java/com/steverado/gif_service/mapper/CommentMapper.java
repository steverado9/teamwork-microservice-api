package com.steverado.gif_service.mapper;

import com.steverado.gif_service.dto.CommentDto;
import com.steverado.gif_service.entity.GifComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    GifComment toGifCommentEntity(CommentDto commentDto);
}
