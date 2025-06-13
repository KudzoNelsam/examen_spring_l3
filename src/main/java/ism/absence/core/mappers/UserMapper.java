package ism.absence.core.mappers;

import ism.absence.core.dto.request.UserRequestDTO;
import ism.absence.core.dto.response.UserResponseDTO;
import ism.absence.data.enums.UserRole;
import ism.absence.data.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    User toEntity(UserRequestDTO userRequestDto);
    UserResponseDTO toResponseDTO(User user);

    default UserRole mapRole(String role) {
        return role != null ? UserRole.valueOf(role.toUpperCase()) : null;
    }
}