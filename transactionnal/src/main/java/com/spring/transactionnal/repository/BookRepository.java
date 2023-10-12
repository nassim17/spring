package com.spring.transactionnal.repository;

import com.spring.transactionnal.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
