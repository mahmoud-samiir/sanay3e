package com.twintech.sanay3e.Repository;

import com.twintech.sanay3e.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
