package com.insurance.claimservice.service;

import com.insurance.claimservice.errorhandler.ClaimExistsException;
import com.insurance.claimservice.errorhandler.ClaimNotFoundException;
import com.insurance.claimservice.model.Claim;

import java.time.LocalDate;
import java.util.List;

public interface ClaimService {

    Claim createClaim(Claim newClaim) throws ClaimExistsException;

    Claim getClaimById(Integer id) throws ClaimNotFoundException;

    List<Claim> getClaimsByPolicyId(String policyId) throws ClaimNotFoundException;

    List<Claim> getClaimsByHospitalAndClaimDate(String hospitalId, LocalDate claimDate) throws ClaimNotFoundException;

    Claim updateClaimStatus(int claimId, String status) throws ClaimNotFoundException;
}
