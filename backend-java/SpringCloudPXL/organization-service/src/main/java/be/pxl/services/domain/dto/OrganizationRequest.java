package be.pxl.services.domain.dto;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import jakarta.persistence.Transient;

import java.util.List;

public class OrganizationRequest {
    private String name;
    private String address;
    @Transient
    private List<Employee> employees;
    @Transient
    private List<Department> departments;
}
