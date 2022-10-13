package booksreview.mappers;

import booksreview.dto.AuthorsDto;
import booksreview.entities.Authors;

import org.modelmapper.ModelMapper;


public interface AuthorsMapper {
    ModelMapper modelMapper = new ModelMapper();

    AuthorsDto authorToDto(Authors author);

    Authors authorDtoToEntity(AuthorsDto authorDto); //IfNeeded
}
