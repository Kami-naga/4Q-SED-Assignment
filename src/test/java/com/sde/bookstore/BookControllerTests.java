package com.sde.bookstore;

import com.sde.bookstore.controller.BookController;
import com.sde.bookstore.domain.Book;
import com.sde.bookstore.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class BookControllerTests {

    @Mock
    private BookService bookService;

    @Autowired
    @InjectMocks
    private BookController bookController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @Before
    public void setupMockMvc(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockHttpSession = new MockHttpSession();
    }

    @Test
    public void testGetAllBooks() throws Exception{
        ArrayList<Book> bookList = new ArrayList<>();
        Book book1 = new Book();
        book1.setBookName("test");
        book1.setPrice(BigDecimal.valueOf(999));
        book1.setStock(20);
        Book book2 = new Book();
        book2.setBookName("test2");
        book2.setPrice(BigDecimal.valueOf(222));
        book2.setStock(30);
        bookList.add(book2);

        when(bookService.findAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/books/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"bookId\":4,\"price\":2.00,\"stock\":10,\"name\":\"tt\"},{\"bookId\":5,\"price\":20.00,\"stock\":30,\"name\":\"aa\"}]"))
                .andDo(print())
                .andReturn();
    }
}
