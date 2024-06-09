package ru.aabelimov.bills.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.bills.dto.CreateOrUpdateUserDto;
import ru.aabelimov.bills.entity.User;

@Component
public class UserMapper {

    public User toEntity(CreateOrUpdateUserDto dto) {
        User user = new User();
        user.setUsername(dto.numberPhone());
        user.setName(dto.name());
        user.setNumberPhone(dto.numberPhone());
        user.setComment(dto.comment());
        return user;
    }
}
