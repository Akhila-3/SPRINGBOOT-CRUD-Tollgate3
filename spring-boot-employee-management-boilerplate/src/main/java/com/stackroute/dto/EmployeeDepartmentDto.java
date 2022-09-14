package com.stackroute.dto;

/*
 The Dto should be used for updating employee department
 */

public class EmployeeDepartmentDto {
    private Integer employeeId;
    private String departmentName;

    public EmployeeDepartmentDto() {
    }

    public EmployeeDepartmentDto(Integer employeeId, String departmentName) {
        this.employeeId = employeeId;
        this.departmentName = departmentName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
