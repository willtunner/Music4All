package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.enun.Role;
import com.music4all.Music4All.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where name ilike :name% ", nativeQuery = true)
    List<User> findByName(@Param("name") String name);

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

    @Transactional
    @Modifying
    @Query(value = " UPDATE users SET deleted = true WHERE id = :id ; ", nativeQuery = true)
    void deletedTrueUser(@Param("id") Long id);
}
