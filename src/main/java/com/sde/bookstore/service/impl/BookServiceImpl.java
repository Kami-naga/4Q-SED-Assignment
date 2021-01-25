package com.sde.bookstore.service.impl;

import com.sde.bookstore.domain.Book;
import com.sde.bookstore.forms.BookForm;
import com.sde.bookstore.repository.BookRepository;
import com.sde.bookstore.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> addBook(BookForm bookForm) {
        Book book = new Book();
        BeanUtils.copyProperties(bookForm,book);
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> updateBook(BookForm updateForm) {
        return bookRepository.findByBookId(updateForm.getBookId()).map(book -> {
            BeanUtils.copyProperties(updateForm,book);
            return Optional.of(bookRepository.save(book));
        }).orElseGet(Optional::empty);
    }

    @Override
    public Boolean deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
        return Boolean.TRUE;
    }
}
