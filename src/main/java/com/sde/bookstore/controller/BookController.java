package com.sde.bookstore.controller;

import com.sde.bookstore.VO.BookVO;
import com.sde.bookstore.domain.User;
import com.sde.bookstore.enums.UserRoleType;
import com.sde.bookstore.forms.BookForm;
import com.sde.bookstore.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value="get all books")
    @GetMapping("/")
    public ResponseEntity<List<BookVO>> getAllBooks(){
        List<BookVO> bookVOList = new ArrayList<>();
        bookService.findAllBooks().forEach(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            bookVOList.add(bookVO);
        });
        return new ResponseEntity<>(bookVOList, HttpStatus.OK);
    }


    @ApiOperation(value="add new books")
    @PostMapping("/new")
    public ResponseEntity<BookVO> addBook(@RequestBody BookForm bookForm, HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return bookService.addBook(bookForm).map(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book,bookVO);
            return new ResponseEntity<>(bookVO,HttpStatus.CREATED);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @ApiOperation(value="update books")
    @PostMapping("/update")
    public ResponseEntity<BookVO> updateBook(@RequestBody BookForm bookForm,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return bookService.updateBook(bookForm).map(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book,bookVO);
            return new ResponseEntity<>(bookVO,HttpStatus.CREATED);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @ApiOperation(value="delete books")
    @DeleteMapping("/")
    public ResponseEntity<Boolean> deleteBook(@RequestParam("bookId") Long bookId,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(bookService.deleteBook(bookId)) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
