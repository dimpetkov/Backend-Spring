package booksreview.mappers;

import booksreview.dto.CategoriesDto;
import booksreview.entities.Categories;
import org.modelmapper.ModelMapper;

public interface CategoriesMapper {
    ModelMapper modelMapper = new ModelMapper();

    CategoriesDto categoryToDto(Categories category);

    Categories categoryDtoToEntity(CategoriesDto categoryDto);
}
