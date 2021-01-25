package com.sde.bookstore.repository;

import com.sde.bookstore.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book,Long> {

    Optional<Book> findByBookId(Long bookId);
}
