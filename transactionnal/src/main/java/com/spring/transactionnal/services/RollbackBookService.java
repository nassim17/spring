package com.spring.transactionnal.services;

import com.spring.transactionnal.entity.Book;
import com.spring.transactionnal.repository.BookRepository;
import java.sql.SQLDataException;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RollbackBookService {

  private final BookRepository bookRepository;


  /**
   * Method showing the default behavior of a checked exception in a transaction.
   * In this case we save a book, then we trigger a checked exception.
   * Result: The book is saved and is not rolled back.
   *
   * @param book
   * @throws SQLException
   */
  @Transactional
  public void saveBookCheckedExceptionByDefault(Book book) throws SQLException {
    bookRepository.save(book);
    throw new SQLException("Throwing exception for demoing Rollback!!!");
  }

  /**
   * Method with a rollBack configuration for a checked exception.
   * In this case we save a book, then we trigger a checked exception.
   * Result: The book is not saved, it is rolled back.
   *
   * @param book
   * @throws SQLDataException
   */
  @Transactional(rollbackFor = {SQLDataException.class})
  public void saveBookCheckedExceptionCustomRollback(Book book) throws SQLDataException {
    bookRepository.save(book);
    throw new SQLDataException("Throwing exception for demoing Rollback!!!");
  }

  /**
   * Method showing the default behavior of an unchecked exception in a transaction.
   * In this case we save a book, then we trigger an unchecked exception.
   * Result: The book is not saved, it is rolled back.
   *
   * @param book
   */
  @Transactional
  public void saveBookUncheckedExceptionByDefault(Book book){
    bookRepository.save(book);
    throw new DataIntegrityViolationException("Throwing exception for demoing rollback");
  }

  /**
   * Method with a rollBack configuration for an unchecked exception.
   * In this case we save a book, then we trigger an unchecked exception.
   * Result: The book is saved and is not rolled back.
   *
   * @param book
   */
  @Transactional(noRollbackFor = { DuplicateKeyException.class })
  public void saveBookUncheckedExceptionCustomNoRollback(Book book){
    bookRepository.save(book);
    throw new DuplicateKeyException("Throwing exception for demoing rollback");
  }
}
