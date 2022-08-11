package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Getter
    private final ItemRepository repository;

    @Getter
    private final CommentRepository commentRepository;

    @Override
    public Item addItem(Item item) {
        repository.save(item);
        return repository.getById(item.getId());
    }

    @Override
    public Item updateItem(ItemDto itemDto, long ownerId, long itemId) {
        if (ownerId == Objects.requireNonNull(repository.getById(itemId)).getOwner().getId()) {
            if (itemDto.isAvailable() == null) {
                itemDto.setAvailable(repository.getById(itemId).getAvailable());
            }
            if (itemDto.getName() == null) {
                itemDto.setName(repository.getById(itemId).getName());
            }
            if (itemDto.getDescription() == null) {
                itemDto.setDescription(repository.getById(itemId).getDescription());
            }
            itemDto.setId(itemId);
            repository.save(ItemMapper.toItem(itemDto, ownerId));
            return repository.getById(itemId);
        }
        throw new NotFoundException("");
    }

    @Override
    public ItemDto getItemDtoById(long itemId) {
        if (repository.existsById(itemId)) {
            return ItemMapper.toItemDto(repository.getById(itemId));
        }
        return null;
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text.equals("")) return new LinkedList<>();
        List<Item> itemList = repository.
                searchWithParams(text);//,text,true);
        List<ItemDto> answerList = new LinkedList<>();
        for (Item item : itemList) {
            answerList.add(ItemMapper.toItemDto(item));
        }
        return answerList;
    }

    @Override
    public Set<ItemDto> show(long id) {
        Set<ItemDto> answer = new HashSet<>();
        for (Item item : repository.findAll()) {
            if (item.getOwner().getId() == id) {
                answer.add(ItemMapper.toItemDto(item));
            }
        }
        return answer;
    }

    @Override
    public Item getItemById(long itemId) {
        if (repository.findById(itemId).isPresent()) {
            return repository.getById(itemId);
        } else throw new NotFoundException("Item with id " + itemId + " is not exist");
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long itemId, long userId) {
        Comment comment = CommentMapper.toComment(commentDto, itemId, userId);
        commentRepository.save(comment);
        return CommentMapper.toCommentDto(commentRepository.getById(comment.getId()));
    }
}
