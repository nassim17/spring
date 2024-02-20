package com.spring.archunit.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spring.archunit.dto.BookDTO;
import com.spring.archunit.entity.Book;
import com.spring.archunit.mapper.BookMapper;
import com.spring.archunit.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::bookToBookDTO)
                .orElse(null);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book newBook = bookMapper.bookDTOToBook(bookDTO);
        Book savedBook = bookRepository.save(newBook);
        return bookMapper.bookToBookDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        if (bookRepository.existsById(id)) {
            Book updatedBook = bookMapper.bookDTOToBook(bookDTO);
            updatedBook.setId(id);
            Book savedBook = bookRepository.save(updatedBook);
            return bookMapper.bookToBookDTO(savedBook);
        }
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
