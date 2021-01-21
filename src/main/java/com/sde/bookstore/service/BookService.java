package com.sde.bookstore.service;

import com.sde.bookstore.domain.Book;
import com.sde.bookstore.forms.BookForm;

import java.util.Optional;

public interface BookService {

    Iterable<Book> findAllBooks();

    Optional<Book> findBookById(Long id);

    Optional<Book> addBook(BookForm bookForm);

    Optional<Book> updateBook(BookForm updateForm);

    Boolean deleteBook(Long bookId);
}
