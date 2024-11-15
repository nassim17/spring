package com.spring.transactional.services;


import com.spring.transactional.entity.Book;
import com.spring.transactional.repository.BookRepository;
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
