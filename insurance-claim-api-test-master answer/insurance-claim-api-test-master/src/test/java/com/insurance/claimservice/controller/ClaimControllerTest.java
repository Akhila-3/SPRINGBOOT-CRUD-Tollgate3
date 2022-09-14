package com.insurance.claimservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.claimservice.dto.ClaimStatusDto;
import com.insurance.claimservice.errorhandler.ClaimExistsException;
import com.insurance.claimservice.errorhandler.ClaimNotFoundException;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.service.ClaimService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ClaimController.class)
public class ClaimControllerTest {

    private Claim claimOne, claimTwo, claimThree, claimSettled;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimService service;

    @Autowired
    private ObjectMapper mapper;

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
    public void givenClaimDetailsWhenCreatedThenReturnSuccessCode() throws Exception {
        when(service.createClaim(any(Claim.class))).thenReturn(claimOne);

        MvcResult mvcResult = mockMvc.perform(post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(claimOne)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Claim.class))
                .usingRecursiveComparison().isEqualTo(claimOne);
    }

    @Test
    public void givenExistingClaimDetailsWhenCreatedThenReturnConflictCode() throws Exception {
        when(service.createClaim(any(Claim.class))).thenThrow(new ClaimExistsException());

        mockMvc.perform(post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(claimOne)))
                .andExpect(status().isConflict());
    }

    @Test
    public void givenPolicyIdWhenExistsThenReturnClaim() throws Exception {
        when(service.getClaimsByPolicyId(anyString())).thenReturn(List.of(claimTwo, claimThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/claims/policy/POL001"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenPolicyIdWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getClaimsByPolicyId(anyString())).thenThrow(new ClaimNotFoundException());

        mockMvc.perform(get("/api/claims/policy/POL999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenClaimIdWhenExistsThenReturnClaim() throws Exception {
        when(service.getClaimById(anyInt())).thenReturn(claimOne);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/claims/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Claim.class))
                .usingRecursiveComparison().isEqualTo(claimOne);
    }

    @Test
    public void givenClaimIdWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getClaimById(anyInt())).thenThrow(new ClaimNotFoundException());

        mockMvc.perform(get("/api/claims/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenHospitalAndClaimDateWhenExistsThenReturnClaims() throws Exception {
        when(service.getClaimsByHospitalAndClaimDate(anyString(), any(LocalDate.class)))
                .thenReturn(List.of(claimOne, claimThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/claims?hospital=HOSP001&date=2022-01-01"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenStatusWhenClaimExistsThenReturnUpdatedClaim() throws Exception {
        when(service.updateClaimStatus(anyInt(), anyString())).thenReturn(claimSettled);

        MvcResult settled = mockMvc.perform(put("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ClaimStatusDto(1, "SETTLED"))))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(settled.getResponse().getContentAsString(), Claim.class))
                .usingRecursiveComparison().isEqualTo(claimSettled);

    }
}