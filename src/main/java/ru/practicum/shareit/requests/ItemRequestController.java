package ru.practicum.shareit.requests;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.UserController;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@Validated
@RestController
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {
    @Getter
    private static RequestService service;

    public ItemRequestController(RequestService service) {
        ItemRequestController.service = service;
    }

    @PostMapping
    public @Valid ItemRequest addRequest(@RequestHeader("X-Sharer-User-Id") String ownerId,@Valid @RequestBody ItemRequest request) {
        System.out.println("addRequest");
        System.out.println(request);
        if (UserController.getUserService().getUser(Long.parseLong(ownerId)).isPresent()) {
            request.setRequestor(UserController.getUserService().getUser(Long.parseLong(ownerId)).get());
            return service.addRequest(Long.parseLong(ownerId), request);
        }
        throw new ValidationException("");
    }

    @GetMapping
    public @Valid List<ItemRequest> getUserRequests(@RequestHeader("X-Sharer-User-Id") String ownerId) {
        System.out.println("getUserRequests");
        if (UserController.getUserService().getUser(Long.parseLong(ownerId)).isPresent()) {
            return service.getUserRequests(Long.parseLong(ownerId));
        }
        throw new ValidationException("");
    }

    @GetMapping("/all")
    public @Valid List<ItemRequest> showRequests(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                                 @RequestParam(name = "from", defaultValue = "") String from,
                                                 @RequestParam(name = "size", defaultValue = "") String size) {
        if (UserController.getUserService().getUser(Long.parseLong(ownerId)).isPresent()) {
            if(from.equals("") || size.equals("")){
                return service.showRequests(Long.parseLong(ownerId));
            }
            else {
                return service.showRequests(Integer.parseInt(from), Integer.parseInt(size), Long.parseLong(ownerId));
            }
        }
        throw new ValidationException("");
    }

    @GetMapping("/{requestId}")
    public @Valid ItemRequest getRequest(@RequestHeader("X-Sharer-User-Id") String ownerId, @PathVariable("requestId") long id) {
        System.out.println("getRequest");
        if (UserController.getUserService().getUser(Long.parseLong(ownerId)).isPresent()) {
            return service.getRequestById(id);
        }
        throw new ValidationException("");
    }

}
