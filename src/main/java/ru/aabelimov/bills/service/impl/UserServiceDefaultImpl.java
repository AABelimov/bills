package ru.aabelimov.bills.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.bills.dto.CreateOrUpdateUserDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.Role;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.mapper.UserMapper;
import ru.aabelimov.bills.repository.UserRepository;
import ru.aabelimov.bills.service.OrderService;
import ru.aabelimov.bills.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderService orderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User %s not found!"));
    }

    @Override
    public User createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        User user = userMapper.toEntity(createOrUpdateUserDto);
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(); //TODO:: add exception
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAllWithoutAdminOrderByIdDesc();
    }

    @Override
    public List<User> findByNameOrNumberPhone(String nameOrNumberPhone) {
        return userRepository.findByNameOrNumberPhoneWithoutAdminOrderByIdDesc(nameOrNumberPhone);
    }

    @Override
    public void updateUser(Long id, CreateOrUpdateUserDto dto) {
        User user = getUserById(id);
        if (!dto.name().isBlank()) {
            user.setName(dto.name());
        }
        if (!dto.numberPhone().isBlank()) {
            user.setNumberPhone(dto.numberPhone());
        }
        if (!dto.comment().isBlank()) {
            user.setComment(dto.comment());
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        User user = getUserById(id);
        List<Order> orders = orderService.getOrdersByUserId(id);
        orders.forEach(orderService::removeOrder);
        userRepository.delete(user);
    }
}
