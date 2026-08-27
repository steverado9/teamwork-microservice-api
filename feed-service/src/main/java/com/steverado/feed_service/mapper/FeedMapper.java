package com.steverado.feed_service.mapper;

import com.steverado.feed_service.dto.FeedItemDto;
import com.steverado.feed_service.entity.Article;
import com.steverado.feed_service.entity.Gif;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    @Mapping(target = "createdOn", source = "createdAt")
    @Mapping(target = "authorId", source = "userId")
    FeedItemDto feedArticle(Article article);

    @Mapping(target = "content", source = "imageUrl")
    @Mapping(target = "createdOn", source = "createdAt")
    @Mapping(target = "authorId", source = "userId")
    FeedItemDto feedGif(Gif gif);
}
