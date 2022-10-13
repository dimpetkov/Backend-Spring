package booksreview.mappers;

import booksreview.dto.LanguagesDto;
import booksreview.entities.Languages;

import org.modelmapper.ModelMapper;

public interface LanguagesMapper {
    ModelMapper modelMapper = new ModelMapper();

    LanguagesDto languagesToDto(Languages language);

    Languages languageDtoToEntity(LanguagesDto languageDto);

}
