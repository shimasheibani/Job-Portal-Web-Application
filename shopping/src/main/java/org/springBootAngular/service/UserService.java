package org.springBootAngular.service;


import org.springBootAngular.dto.LoginRequest;
import org.springBootAngular.dto.RegisterRequest;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.UserDTO;
import org.springBootAngular.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUser();
    User getCurentLoggedUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransaction(Long id);

}
