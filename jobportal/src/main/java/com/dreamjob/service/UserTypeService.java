package com.dreamjob.service;

import com.dreamjob.entity.UserType;
import com.dreamjob.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTypeService {
    private  final UserTypeRepository userTypeRepository ;
    public List<UserType> getAll() {
        return  userTypeRepository.findAll();
    }

}
