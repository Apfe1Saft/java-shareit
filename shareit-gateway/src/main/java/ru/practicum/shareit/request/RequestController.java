package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class RequestController {

    private final RequestClient requestsClient;

    @PostMapping
    public Object addRequest(@RequestBody ItemRequestDto itemRequestDto,
                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (itemRequestDto.getDescription() == null) throw new WrongDataException("");
        return requestsClient.addRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Object getUserRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (userId == null) {
            throw new NullPointerException("Не указан id пользователя.");
        } else {
            return requestsClient.getUserRequests(userId);
        }
    }

    @GetMapping("/all")
    public Object showRequests(@RequestParam(defaultValue = "") String from,
                         @RequestParam(defaultValue = "") String size,
                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if(from.equals("") && size.equals("")) return requestsClient.showRequests(userId, from, size);
        if (Integer.parseInt(from) < 0 || Integer.parseInt(size) < 0) {
            throw new WrongDataException("");
        }
        if (Integer.parseInt(size) == 0) {
            throw new WrongDataException("");
        }
        return requestsClient.showRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public Object getRequest(@PathVariable int requestId,
                          @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (userId == null) {
            throw new NullPointerException("Не указан id пользователя.");
        } else {
            return requestsClient.getRequest(userId, requestId);
        }
    }
}