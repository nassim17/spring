package com.spring.archunit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.archunit.entity.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
}
