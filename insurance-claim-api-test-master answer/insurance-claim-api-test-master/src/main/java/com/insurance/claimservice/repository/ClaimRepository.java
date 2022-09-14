package com.insurance.claimservice.repository;

import com.insurance.claimservice.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/*
Add appropriate annotation/s and implement required interface/s to create a bean for handling database interactions using Data JPA
*/
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    /* **USE FIND KEYWORD SYNTAX to define data access methods** */

    /*
         Define a method to get claim by policy id and claim date, returning an Optional of claim
    */
    Optional<Claim> findClaimByPolicyIdAndClaimDate(String policyId, LocalDate claimDate);

    /*
        Define a method to get all claims by policy id, returning list of claims
    */
    List<Claim> findClaimByPolicyId(String policyId);

    /*
        Define a method to get all claims by hospital id and claim date, returning list of claims
    */
    List<Claim> findClaimByHospitalIdAndClaimDate(String hospitalId, LocalDate claimDate);
}