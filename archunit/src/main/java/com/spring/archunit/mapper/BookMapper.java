package com.spring.archunit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spring.archunit.dto.BookDTO;
import com.spring.archunit.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true) // Ignore l'ID lors de la conversion de DTO à entité pour la création
    BookDTO bookToBookDTO(Book book);

    Book bookDTOToBook(BookDTO bookDTO);
}
