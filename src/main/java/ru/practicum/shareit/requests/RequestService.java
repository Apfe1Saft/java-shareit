package ru.practicum.shareit.requests;

import java.util.List;

public interface RequestService {
    ItemRequest addRequest(long userId,ItemRequest request);
    List<ItemRequest> getUserRequests(long userId);
    List<ItemRequest> showRequests(long firstRequestId, int size, long userId);

    ItemRequest getRequestById(long requestId );
}
