package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
//@Profile("test")
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
    public ItemDto getItemDtoById(long itemId, long userId) {
        if (repository.existsById(itemId)) {
            ItemDto answer = ItemMapper.toItemDto(repository.getById(itemId));
            if (BookingController.getBookingService().showOwnerBookings(userId, State.PAST).stream().anyMatch(x -> x.getItem().getId() == itemId) &&
                    BookingController.getBookingService().showOwnerBookings(userId, State.FUTURE).stream().anyMatch(x -> x.getItem().getId() == itemId)
            ) {
                BookingDto lastBooking = BookingMapper.toBookingDto(BookingController.getBookingService().showOwnerBookings(repository.getById(itemId).
                        getOwner().getId(), State.PAST).stream().filter(x -> x.getItem().getId() == itemId).findFirst().get());
                BookingDto nextBooking = BookingMapper.toBookingDto(BookingController.getBookingService().showOwnerBookings(repository.getById(itemId).
                        getOwner().getId(), State.FUTURE).stream().filter(x -> x.getItem().getId() == itemId).findFirst().get());
                lastBooking.setBookerId(BookingController.getBookingService().getBooking(lastBooking.getId()).getBooker().getId());
                nextBooking.setBookerId(BookingController.getBookingService().getBooking(nextBooking.getId()).getBooker().getId());
                answer.setLastBooking(lastBooking);
                answer.setNextBooking(nextBooking);
            }
            if (getComment(itemId) != null) {
                List<CommentDto> commentDto = answer.getComments();
                commentDto.add(CommentMapper.toCommentDto(getComment(itemId)));
                answer.setComments(commentDto);
            }
            return answer;
        }
        return null;
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text.equals("")) return new LinkedList<>();
        List<Item> itemList = repository.
                searchWithParams(text);
        List<ItemDto> answerList = new LinkedList<>();
        for (Item item : itemList) {
            answerList.add(ItemMapper.toItemDto(item));
        }
        return answerList;
    }

    @Override
    public List<ItemDto> show(long id) {
        List<ItemDto> answer = new LinkedList<>();
        for (Item item : repository.findAll()) {
            if (item.getOwner().getId() == id) {
                answer.add(getItemDtoById(item.getId(), id));
            }
        }
        return answer.stream().sorted(Comparator.comparing(ItemDto::getId)).collect(Collectors.toList());
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
        for (Booking booking : BookingController.getBookingService().showAllUserBookings(userId, State.ALL)) {
            if (booking.getItem().getId() == itemId && booking.getEnd().isBefore(LocalDateTime.now())) {
                commentRepository.save(comment);
                return CommentMapper.toCommentDto(commentRepository.getById(comment.getId()));
            }
        }
        throw new WrongDataException("The user can not set comment about the item.");
    }

    @Override
    public Comment getComment(long itemId) {
        if (commentRepository.findAll().stream().anyMatch(x -> x.getItem().getId() == itemId)) {
            return commentRepository.findAll().stream().filter(x -> x.getItem().getId() == itemId).findFirst().get();
        }
        return null;
    }
}
