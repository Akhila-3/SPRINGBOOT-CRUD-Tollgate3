package com.insurance.claimservice.commander.repository;

import com.insurance.claimservice.commander.MethodExecutor;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.repository.ClaimRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClaimRepositoryTest {


    @Autowired
    private ClaimRepository claimRepository;

    private Claim claimOne, claimTwo, claimThree;

    @BeforeEach
    public void setUp() {
        claimOne = new Claim(0, "POL001", "HOSP001", LocalDate.now(), "NEW", 10000);
        claimTwo = new Claim(0, "POL002", "HOSP002", LocalDate.now(), "NEW", 20000);
        claimThree = new Claim(0, "POL002", "HOSP001", LocalDate.now(), "NEW", 20000);

        MethodExecutor.executeMethod(claimRepository, "save",
                new Class[]{Object.class},
                new Object[]{claimOne});
        MethodExecutor.executeMethod(claimRepository, "save",
                new Class[]{Object.class},
                new Object[]{claimTwo});
        MethodExecutor.executeMethod(claimRepository, "save",
                new Class[]{Object.class},
                new Object[]{claimThree});
    }

    @Test
    public void givenClaimIdWhenNotExistsThenReturnEmptyOptional() {
        Optional<Claim> optionalClaim = (Optional<Claim>) MethodExecutor.executeMethod(claimRepository, "findById",
                new Class[]{Object.class},
                new Object[]{999});
        assertThat(optionalClaim).isEmpty();
    }

    @Test
    public void givenPolicyIdWhenExistsThenReturnOptionalWithClaim() {
        List<Claim> claims = (List<Claim>) MethodExecutor.executeMethod(claimRepository, "findClaimByPolicyId",
                new Class[]{String.class},
                new Object[]{"POL002"});
        assertThat(claims.size()).isEqualTo(2);
    }

    @Test
    public void givenPolicyIdWhenNotExistsThenReturnEmptyList() {

        List<Claim> claims = (List<Claim>) MethodExecutor.executeMethod(claimRepository, "findClaimByPolicyId",
                new Class[]{String.class},
                new Object[]{"POL999"});
        assertThat(claims.size()).isZero();

    }

    @Test
    public void givenPolicyIdAndClaimDateWhenExistsThenReturnOptionalWithClaim() {
        Optional<Claim> optionalClaim = (Optional<Claim>) MethodExecutor.executeMethod(claimRepository, "findClaimByPolicyIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"POL001", LocalDate.now()});
        assertThat(optionalClaim).isPresent();
        assertThat(optionalClaim.get().getHospitalId()).isEqualTo("HOSP001");
    }

    @Test
    public void givenHospitalIdAndClaimDateWhenExistsThenReturnList() {
        List<Claim> claims = (List<Claim>) MethodExecutor.executeMethod(claimRepository, "findClaimByHospitalIdAndClaimDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"HOSP001", LocalDate.now()});

        assertThat(claims.size()).isEqualTo(2);

    }


    @AfterEach
    public void tearDown() {
        MethodExecutor.executeMethod(claimRepository, "deleteAll",
                new Class[]{},
                new Object[]{});
        claimOne = null;
        claimTwo = null;
        claimThree = null;

    }
}