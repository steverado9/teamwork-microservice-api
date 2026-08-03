package com.steverado.user_service.repository;

import com.steverado.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO users (first_name, last_name, email, password, gender, role, department, address, created_at)
            VALUES (:firstName, :lastName, :email, :password, :gender, :role, :department, :address, CURRENT_TIMESTAMP)
            """, nativeQuery = true)
    void saveUser(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("password") String password,
            @Param("gender") String gender,
            @Param("role") String role,
            @Param("department") String department,
            @Param("address")String address
    );

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
