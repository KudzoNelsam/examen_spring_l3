package ism.absence.services;

import ism.absence.data.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User userRequestDTO);
    Optional<User> findById(String id);
    List<User> findAll(Pageable pageable);
    Optional<User> login(String username, String password);
}