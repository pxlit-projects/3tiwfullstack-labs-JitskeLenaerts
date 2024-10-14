package be.pxl.services.domain.dto;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {
    private String name;
    private String address;
    @Transient
    private List<Employee> employees;
    @Transient
    private List<Department> departments;
}
