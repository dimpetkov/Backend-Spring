package booksreview.mappers;

import booksreview.dto.BooksDto;
import booksreview.entities.Books;

import org.modelmapper.ModelMapper;

public interface BooksMapper {
    ModelMapper modelMapper = new ModelMapper();

    public abstract BooksDto bookToDto(Books book);

    Books bookDtotoToEntity(BooksDto articleDto);
}
