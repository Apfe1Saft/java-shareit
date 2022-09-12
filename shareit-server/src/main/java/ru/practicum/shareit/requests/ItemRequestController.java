package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserService;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {

    private final RequestService service;
    private final UserService userService;

    @PostMapping
    public  ItemRequest addRequest(@RequestHeader("X-Sharer-User-Id") String ownerId,  @RequestBody ItemRequest request) {
        if (userService.getUser(Long.parseLong(ownerId)).isPresent()) {
            request.setRequestor(userService.getUser(Long.parseLong(ownerId)).get());
            return service.addRequest(Long.parseLong(ownerId), request);
        }
        throw new ValidationException("");
    }

    @GetMapping
    public  List<ItemRequest> getUserRequests(@RequestHeader("X-Sharer-User-Id") String ownerId) {
        if (userService.getUser(Long.parseLong(ownerId)).isPresent()) {
            return service.getUserRequests(Long.parseLong(ownerId));
        }
        throw new ValidationException("");
    }

    @GetMapping("/all")
    public  List<ItemRequest> showRequests(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                                 @RequestParam(name = "from", defaultValue = "") String from,
                                                 @RequestParam(name = "size", defaultValue = "") String size) {
        if (userService.getUser(Long.parseLong(ownerId)).isPresent()) {
            if (from.equals("") || size.equals("")) {
                return service.showRequests(Long.parseLong(ownerId));
            } else {
                return service.showRequests(Integer.parseInt(from), Integer.parseInt(size), Long.parseLong(ownerId));
            }
        }
        throw new ValidationException("");
    }

    @GetMapping("/{requestId}")
    public  ItemRequest getRequest(@RequestHeader("X-Sharer-User-Id") String ownerId, @PathVariable("requestId") long id) {
        if (userService.getUser(Long.parseLong(ownerId)).isPresent()) {
            return service.getRequestById(id);
        }
        throw new ValidationException("Request is not exist.");
    }

}
