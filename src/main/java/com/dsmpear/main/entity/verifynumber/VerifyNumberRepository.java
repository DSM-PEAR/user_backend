package com.dsmpear.main.entity.verifynumber;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyNumberRepository extends CrudRepository<VerifyNumber, String> {
    public Optional<VerifyNumber> findByEmail(String email);
}
