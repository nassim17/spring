package com.spring.transactionnal.services;


import com.spring.transactionnal.entity.Book;
import com.spring.transactionnal.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IsolationLevelBookService {

  private final BookRepository bookRepository;


  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void saveBook(Book book){

    bookRepository.save(book);
  }
}
