package ru.practicum.shareit.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class RequestServiceImpl implements RequestService{
    private RequestRepository repository;

    @Override
    public ItemRequest addRequest(long userId,ItemRequest request) {
        repository.save(request);
        return repository.getById(request.getId());
    }

    @Override
    public List<ItemRequest> getUserRequests(long userId) {
        return repository.findAll().stream().filter( x->x.getRequestor().getId()==userId).
                collect(Collectors.toList());
    }

    @Override
    public List<ItemRequest> showRequests(long firstRequestId, int size, long userId) {
        return repository.findAll().stream().filter( x->x.getRequestor().getId()!=userId).
                sorted(Comparator.comparing(ItemRequest::getCreated)).
                collect(Collectors.toList());
    }

    @Override
    public ItemRequest getRequestById(long requestId ) {
        return repository.getById(requestId);
    }
}
