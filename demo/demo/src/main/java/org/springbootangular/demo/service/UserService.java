package org.springbootangular.demo.service;

import org.springbootangular.demo.dto.LoginRequest;
import org.springbootangular.demo.dto.RegisterRequest;
import org.springbootangular.demo.dto.Response;
import org.springbootangular.demo.dto.UserDTO;
import org.springbootangular.demo.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUser();
    User getCurentLoggedUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransaction(Long id);

}
