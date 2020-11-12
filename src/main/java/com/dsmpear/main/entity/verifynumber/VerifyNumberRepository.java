package com.dsmpear.main.entity.verifynumber;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerifyNumberRepository extends CrudRepository<VerifyNumber, String> {
    Optional<VerifyNumber> findByEmail(String email);
}
