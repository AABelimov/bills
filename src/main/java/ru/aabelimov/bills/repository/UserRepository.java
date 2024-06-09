package ru.aabelimov.bills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.bills.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role != 1 AND (u.name LIKE %?1% OR u.numberPhone LIKE %?1%)  ORDER BY u.id DESC")
    List<User> findByNameOrNumberPhoneWithoutAdminOrderByIdDesc(String nameOrNumberPhone);

    @Query("SELECT u FROM User u WHERE u.role != 1 ORDER BY u.id DESC")
    List<User> findAllWithoutAdminOrderByIdDesc();
}
