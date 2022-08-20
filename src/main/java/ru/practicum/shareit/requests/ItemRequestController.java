package ru.practicum.shareit.requests;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemDto;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@Validated
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Getter
    private static RequestService service;

    public ItemRequestController(RequestService service) {
        service = service;
    }

    @PostMapping
    public @Valid void addRequest(@RequestHeader("X-Sharer-User-Id") String ownerId,ItemRequest request) {
         service.addRequest(Long.parseLong(ownerId),request);
    }

    @GetMapping
    public @Valid List<ItemRequest> getUserRequests(@RequestHeader("X-Sharer-User-Id") String ownerId) {
         return service.getUserRequests(Long.parseLong(ownerId));
    }

    @GetMapping("/all")
    public @Valid List<ItemRequest> showRequests(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                             @RequestParam(name = "from") String from,@RequestParam(name = "size") String size) {
         return service.showRequests(Long.parseLong(from),Integer.parseInt(size),Long.parseLong(ownerId));
    }

    @GetMapping("/{requestId}")
    public @Valid ItemRequest getRequest(@RequestHeader("X-Sharer-User-Id") String ownerId,@PathVariable("id") long id) {
         return service.getRequestById(id);
    }

}
