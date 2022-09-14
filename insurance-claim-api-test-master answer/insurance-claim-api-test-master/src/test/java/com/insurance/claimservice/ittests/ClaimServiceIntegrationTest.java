package com.insurance.claimservice.ittests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.claimservice.ClaimServiceApplication;
import com.insurance.claimservice.model.Claim;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ClaimServiceApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClaimServiceIntegrationTest {

    public static final String CLAIMS_BASE_URL = "/api/claims";
    private Claim claimOne;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        claimOne = new Claim(1, "POL001", "HOSP001", LocalDate.now(), "NEW", 10000);
    }

    @AfterEach
    public void tearDown() {
        claimOne = null;
    }

    @Test
    @Order(1)
    public void givenClaimDetailsThenCreateNewClaim() throws Exception {
        mockMvc.perform(
                        post(CLAIMS_BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(claimOne)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(2)
    public void givenPolicyIdWhenClaimExistsThenReturnClaims() throws Exception {
        mockMvc.perform(
                        get(CLAIMS_BASE_URL + "/policy/POL001"))
                .andExpect(status().isOk())
                .andReturn();

    }

    private String toJson(final Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
