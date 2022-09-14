package com.insurance.claimservice.dto;

/*
 The Dto should be used for updating claim status
 */
public class ClaimStatusDto {
    private Integer claimId;
    private String status;

    public ClaimStatusDto() {
    }

    public ClaimStatusDto(Integer claimId, String status) {
        this.claimId = claimId;
        this.status = status;
    }

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
