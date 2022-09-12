package ru.practicum.shareit.item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareIt-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public Object create(ItemDto itemCreateDto, Integer userId) {
        return post("", userId, itemCreateDto);
    }

    public Object update(ItemDto itemCreateDto, Integer userId, Integer itemId) {
        return patch("/" + itemId, userId, itemCreateDto);
    }

    public Object getItemDtoById(Integer userId, Integer itemId) {
        return get("/" + itemId, userId);
    }

    public Object show(Integer userId, String from, String size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("" + "?from=" + from + "&size=" + size, userId, parameters);
    }

    public Object search(Integer userId, String text, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "from", from,
                "size", size
        );
        return get("/search" + "?text=" + text + "&from=" + from + "&size=" + size, userId, parameters);
    }

    public Object addComment(Integer userId, Integer itemId, CommentDto commentCreateDto) {
        return post("/" + itemId + "/comment", userId, commentCreateDto);
    }
}