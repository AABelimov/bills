package ru.aabelimov.bills.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.aabelimov.bills.dto.CreateOrUpdateUserDto;
import ru.aabelimov.bills.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    User getUserById(Long id);

    List<User> getUsers();

    List<User> findByNameOrNumberPhone(String nameOrNumberPhone);

    void updateUser(Long id, CreateOrUpdateUserDto dto);

    void removeUserById(Long id);
}
