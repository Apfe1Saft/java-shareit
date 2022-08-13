package ru.practicum.shareit.comment;

import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.user.UserController;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto, long itemId,long userid) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                ItemController.getItemService().getItemById(itemId),
                UserController.getUserService().getUser(userid).get(),
                commentDto.getCreated()
        );

    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreated(),
                comment.getItem().getId(),
                UserController.getUserService().getUser(comment.getAuthor().getId()).get().getName()
        );
    }
}
