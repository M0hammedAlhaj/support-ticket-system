package com.ticket.userservice.user.infrastructre.repository;

import com.ticket.userservice.user.domain.entity.User;
import com.ticket.userservice.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoUserRepositoryAdapter implements UserRepository {
    private final SpringMongoUserRepository springMongoUserRepository;

    @Override
    public void save(User user) {
        springMongoUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springMongoUserRepository.findByEmail(email);
    }
}
