package com.dsmpear.main.entity.verifyuser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyUserRepository extends CrudRepository<VerifyUser, Integer> {
    public Optional<VerifyUser> findByEmail(String email);
}
