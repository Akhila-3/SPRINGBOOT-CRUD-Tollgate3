package com.insurance.claimservice.service;

import com.insurance.claimservice.MethodExecutor;
import com.insurance.claimservice.errorhandler.ClaimExistsException;
import com.insurance.claimservice.errorhandler.ClaimNotFoundException;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.repository.ClaimRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClaimServiceImplTest {

    @Mock
    private ClaimRepository repository;


    @InjectMocks
    private ClaimServiceImpl service;

    private Claim claimOne, claimTwo, claimThree,claimSettled;

    @BeforeEach
    public void setUp() {
        claimOne = new Claim(1, "POL001", "HOSP001", LocalDate.now(), "NEW", 10000);
        claimTwo = new Claim(2, "POL002", "HOSP002", LocalDate.now(), "NEW", 20000);
        claimThree = new Claim(3, "POL002", "HOSP001", LocalDate.now(), "NEW", 20000);
        claimSettled = new Claim(1, "POL001", "HOSP001", LocalDate.now(), "SETTLED", 10000);

    }

    @AfterEach
    public void tearDown() {
        claimOne = null;
        claimTwo = null;
        claimThree = null;
    }

    @Test
    public void givenClaimWhenDoesNotExistThenReturnCreatedClaim() throws ClaimExistsException {
        when(MethodExecutor.executeMethod(repository, "findClaimByPolicyIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.empty());
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Claim.class)}))
                .thenReturn(claimOne);

        Claim claim = service.createClaim(claimOne);
        assertEquals(claim, claimOne);


    }


    @Test
    public void givenClaimWhenExistsThenThrowException() throws ClaimExistsException {
        when(MethodExecutor.executeMethod(repository, "findClaimByPolicyIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.of(claimOne));

        assertThrows(ClaimExistsException.class, () -> service.createClaim(claimOne));
    }

    @Test
    public void givenClaimIdWhenExistsThenReturnClaim() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.of(claimOne));

        assertEquals(service.getClaimById(1), claimOne);
    }

    @Test
    public void givenClaimIdWhenDoesNotExistThenThrowException() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> service.getClaimById(9));
    }


    @Test
    public void givenPolicyIdWhenExistsThenReturnListOfClaim() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findClaimByPolicyId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(List.of(claimTwo, claimThree));

        List<Claim> claimsByPolicyId = service.getClaimsByPolicyId("POL002");
        assertEquals(2, claimsByPolicyId.size());
    }

    @Test
    public void givenPolicyIdWhenDoesNotExistThenThrowException() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findClaimByPolicyId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(Collections.emptyList());

        assertThrows(ClaimNotFoundException.class, () -> service.getClaimsByPolicyId("POL009"));
    }


    @Test
    public void givenHospitalIdAndClaimDateWhenExistsThenReturnListOfClaim() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findClaimByHospitalIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(List.of(claimTwo, claimThree));

        List<Claim> claims = service.getClaimsByHospitalAndClaimDate("HOSP001", LocalDate.now());
        assertEquals(2, claims.size());
    }

    public void givenHospitalIdAndClaimDateWhenDoesNotExistThenThrowException() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findClaimByHospitalIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Collections.emptyList());

        assertThrows(ClaimNotFoundException.class, () -> service.getClaimsByHospitalAndClaimDate("HOSP009",LocalDate.now()));
    }

    @Test
    public void givenClaimIdWhenExistsThenReturnUpdatedClaim() throws ClaimNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{1}))
                .thenReturn(Optional.of(claimOne));
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Claim.class)}))
                .thenReturn(claimSettled);

        assertEquals(claimSettled,service.updateClaimStatus(1, "SETTLED"));

    }

}