package ru.practicum.shareit.comment.mapper;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.entity.Comment;

import java.util.function.Function;

public class CommentMapper implements Function<Comment, CommentDto> {

    @Override
    public CommentDto apply(Comment comment) {
        return CommentDto.builder()
                .withId(comment.getId())
                .withAuthorName(comment.getUser().getName())
                .withText(comment.getText())
                .withCreated(comment.getCreated())
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentMapper().apply(comment);
    }
}
