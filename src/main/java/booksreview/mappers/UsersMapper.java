package booksreview.mappers;

import booksreview.dto.UsersDto;
import booksreview.entities.Users;
import org.modelmapper.ModelMapper;

public interface UsersMapper {
    ModelMapper modelMapper = new ModelMapper();

    UsersDto userToDto(Users user);

    Users userDtoToEntoty(UsersDto userDto);//To skipp ID and created_at
}
