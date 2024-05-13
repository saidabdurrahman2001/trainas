package asik.propensik.trainnas.dto;

import org.mapstruct.Mapper;

import asik.propensik.trainnas.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserModel createUserRequestDTOToUser(UserDTO userDTO);
}
