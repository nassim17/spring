package com.spring.transactionnal.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.transactionnal.entity.Book;
import com.spring.transactionnal.repository.BookRepository;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
class RollbackBookServiceTests {

  @Autowired private RollbackBookService rollbackBookService;
  @Autowired private BookRepository bookRepository;


  @Test
  void saveBookCheckedExceptionByDefaultTest(){
    Book book = new Book();
    book.setId(1L);
    book.setTitle("Book 1");
    book.setEdition("Edition 1");

    assertThrows(SQLException.class, () -> rollbackBookService.saveBookCheckedExceptionByDefault(book));

    Optional<Book> createdBookOpt = bookRepository.findById(book.getId());
    assertTrue(createdBookOpt.isPresent());
  }

  @Test
  void saveBookCheckedExceptionCustomRollbackTest(){
    Book book = new Book();
    book.setId(2L);
    book.setTitle("Book 2");
    book.setEdition("Edition 2");

    assertThrows(SQLDataException.class, () -> rollbackBookService.saveBookCheckedExceptionCustomRollback(book));

    Optional<Book> createdBookOpt = bookRepository.findById(book.getId());
    assertTrue(createdBookOpt.isEmpty());
  }

  @Test
  void saveBookUncheckedExceptionByDefaultTest(){
    Book book = new Book();
    book.setId(3L);
    book.setTitle("Book 3");
    book.setEdition("Edition 3");

    assertThrows(DataIntegrityViolationException.class, () -> rollbackBookService.saveBookUncheckedExceptionByDefault(book));

    Optional<Book> createdBookOpt = bookRepository.findById(book.getId());
    assertTrue(createdBookOpt.isEmpty());
  }

  @Test
  void saveBookUncheckedExceptionCustomNoRollbackTest(){
    Book book = new Book();
    book.setId(4L);
    book.setTitle("Book 4");
    book.setEdition("Edition 4");

    assertThrows(DuplicateKeyException.class, () -> rollbackBookService.saveBookUncheckedExceptionCustomNoRollback(book));

    Optional<Book> createdBookOpt = bookRepository.findById(book.getId());
    assertTrue(createdBookOpt.isPresent());
  }
}
