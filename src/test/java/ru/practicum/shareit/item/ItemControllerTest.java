package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @Mock
    private ItemService itemService;
    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemController itemController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mvc = MockMvcBuilders.standaloneSetup(itemController).build();
        item = new Item(1, "name", "descr", true,
                new User(1, "name", "a@email.ru"));
        itemDto = ItemMapper.toItemDto(item);
    }

    @Test
    void create() throws Exception {
        when(itemService.addItem(any())).thenReturn(item);
        try (MockedStatic<ItemMapper> utilities = Mockito.mockStatic(ItemMapper.class)) {
            utilities.when(() -> ItemMapper.toItem(any(), anyLong())).thenReturn(item);
            utilities.when(() -> ItemMapper.toItemDto(any())).thenReturn(itemDto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(post("/items")
                    .headers(headers)
                    .content(mapper.writeValueAsString(itemDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                    .andExpect(jsonPath("$.name", is(item.getName())))
                    .andExpect(jsonPath("$.description", is(item.getDescription())));
        }

    }

    @Test
    void show() throws Exception {
        List<ItemDto> items = new LinkedList<>();
        items.add(new ItemDto(1, "name", "descr", true));
        when(itemService.show(anyLong())).thenReturn(items);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(get("/items")
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    void showPageable() throws Exception {
        List<Item> items = new LinkedList<>();
        items.add(item);
        when(itemService.show(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(items));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(get("/items")
                .headers(headers)
                .param("from", "0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    void update() throws Exception {
        when(itemService.updateItem(itemDto, 1L, 1)).thenReturn(item);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(patch("/items/1")
                .headers(headers)
                .content(mapper.writeValueAsString(itemDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    void getItemDtoById() throws Exception {
        when(itemService.getItemDtoById(1, 1L)).thenReturn(itemDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(get("/items/1")
                .headers(headers)
                .content(mapper.writeValueAsString(itemDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    void search() throws Exception {
        List<ItemDto> items = new LinkedList<>();
        items.add(itemDto);
        when(itemService.searchItems("item")).thenReturn(items);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(get("/items/search?text=item")
                .content(mapper.writeValueAsString("item"))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    void searchPageable() throws Exception {
        List<ItemDto> items = new LinkedList<>();
        items.add(itemDto);
        when(itemService.searchItems("item", 0, 1)).thenReturn(items);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(get("/items/search?text=item")
                .headers(headers)
                .param("from", "0")
                .param("size", "1")
                .content(mapper.writeValueAsString("item"))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    void addComment() throws Exception {
        CommentDto commentDto = new CommentDto(1, "text", 1, "Karl");
        when(itemService.addComment(commentDto, 1, 1L))
                .thenReturn(commentDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Sharer-User-Id", "1");
        mvc.perform(post("/items/1/comment")
                .headers(headers)
                .content(mapper.writeValueAsString(commentDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1L), Long.class))
                .andExpect(jsonPath("$.text", is("text")));
    }
}