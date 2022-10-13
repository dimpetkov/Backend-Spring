package booksreview.mappers;

import booksreview.dto.ReviewsDto;
import booksreview.entities.Reviews;
import org.modelmapper.ModelMapper;

import java.util.List;

public interface ReviewsMapper {
    ModelMapper modelMapper = new ModelMapper();

    ReviewsDto reviewToDto(Reviews review);

    List<ReviewsDto> reviewsToDtoList(List<Reviews> reviews);
    List<Reviews> reviewsDtoToEntityList(List<ReviewsDto> reviews);

    Reviews reviewDtoToEntity(ReviewsDto reviewDto);


}
