package com.insurance.claimservice.controller;

import com.insurance.claimservice.dto.ClaimStatusDto;
import com.insurance.claimservice.errorhandler.ClaimExistsException;
import com.insurance.claimservice.errorhandler.ClaimNotFoundException;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*
Add appropriate annotation/s to create a bean for handling http requests for the rest api
 */
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    /*
    Inject the service object here
     */
    @Autowired
    private ClaimService service;

    /*
    Create API endpoints as per the requirement given below
    */

    /*
     description  :  create new claim
     api endpoint : /api/claims
     http request : POST
     request body : claim details
     success response:
            body: created claim    - http status: 201
     failure response:
            If a claim exists with same policy id and claim date
            body: failure message   - http status: 409
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Claim addClaim(@RequestBody Claim claim) throws ClaimExistsException {
        return service.createClaim(claim);
    }

    /*
     description  : get a claim by claim id
     api endpoint : /api/claims/{id}
     http request : GET
     success response:
            body: Claim Details   - http status: 200
     failure response:
            If no claims exists for given criteria
            body: failure message - http status: 404
  */
    @GetMapping("/{claimId}")
    public Claim getClaimById(@PathVariable Integer claimId) throws ClaimNotFoundException {
        return service.getClaimById(claimId);
    }

    /*
     description  : get all claims by policy id
     api endpoint : /api/claims/policy/{policyId}
     http request : GET
     success response:
            body: list of claims    - http status: 200
     failure response:
            if no claims exists for given criteria
            body: failure message   - http status: 404
  */
    @GetMapping("/policy/{policyId}")
    public List<Claim> getClaimsByPolicyId(@PathVariable String policyId) throws ClaimNotFoundException {
        return service.getClaimsByPolicyId(policyId);
    }

    /*
     description  : get all claims by hospital Id and claim date
     api endpoint : /api/claims
     request query parameters: hospital and date
                               ex: <...pathurl>?hospital=xxx&date=yyyy-MM-dd
     http request : GET
     success response:
            body: list of claims    - http status: 200
     failure response:
            if no claims exists for given criteria
            body: failure message   - http status: 404
  */

    @GetMapping
    public List<Claim> getClaimsByHospitalAndClaimDate(@RequestParam("hospital") String hospitalId,
                                                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate claimDate) throws ClaimNotFoundException {
        return service.getClaimsByHospitalAndClaimDate(hospitalId, claimDate);
    }

    /*
     description  : update the claim status for given claim id
     api endpoint : /api/claims
     http request : PUT
     request body : claim id and new claim status
     success response:
            body: updated claim    - http status: 200
     failure response:
            If no claims exists for given criteria
            body: failure message - http status: 404
  */
    @PutMapping
    public Claim updateClaimStatus(@RequestBody ClaimStatusDto claimStatusDto) throws ClaimNotFoundException {
        return service.updateClaimStatus(claimStatusDto.getClaimId(), claimStatusDto.getStatus());
    }
}