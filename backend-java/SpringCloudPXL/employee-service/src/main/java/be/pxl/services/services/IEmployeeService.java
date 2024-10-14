package be.pxl.services.services;


import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Optional<Employee> findById(long employeeId);

    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> findByDepartment(Long departmentId);
    List<EmployeeResponse> findByOrganization(Long organizationId);

    void addEmployee(EmployeeRequest employeeRequest);
}
