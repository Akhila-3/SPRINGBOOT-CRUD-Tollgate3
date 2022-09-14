package com.insurance.claimservice.service;

import com.insurance.claimservice.errorhandler.ClaimExistsException;
import com.insurance.claimservice.errorhandler.ClaimNotFoundException;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/*
Add appropriate annotation/s to create a bean for service layer
Implement all the functionality based on the ClaimService Interface
 */
@Service
@Transactional
public class ClaimServiceImpl implements ClaimService{

    /*
      Inject the repository bean
     */
    @Autowired
    private ClaimRepository repository;

    @Override
    public Claim createClaim(Claim newClaim) throws ClaimExistsException {
        Optional<Claim> optionalClaim = repository.findClaimByPolicyIdAndClaimDate(newClaim.getPolicyId(), newClaim.getClaimDate());
        if(optionalClaim.isPresent()){
            throw new ClaimExistsException();
        }
        newClaim.setClaimDate(LocalDate.now());
        return repository.save(newClaim);
    }

    @Override
    public Claim getClaimById(Integer id) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = repository.findById(id);
        return optionalClaim.orElseThrow(ClaimNotFoundException::new);
    }

    @Override
    public List<Claim> getClaimsByPolicyId(String policyId) throws ClaimNotFoundException {
        List<Claim> claimByPolicyId = repository.findClaimByPolicyId(policyId);
        if(claimByPolicyId.isEmpty()){
            throw new ClaimNotFoundException();
        }
        return claimByPolicyId;
    }

    @Override
    public List<Claim> getClaimsByHospitalAndClaimDate(String hospitalId, LocalDate claimDate) throws ClaimNotFoundException {
        List<Claim> claimList = repository.findClaimByHospitalIdAndClaimDate(hospitalId, claimDate);
        if(claimList.isEmpty()){
            throw new ClaimNotFoundException();
        }
        return claimList;
    }

    @Override
    public Claim updateClaimStatus(int claimId, String status) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = repository.findById(claimId);
        var claim = optionalClaim.orElseThrow(ClaimNotFoundException::new);
        claim.setStatus(status);
        return repository.save(claim);
    }
}
