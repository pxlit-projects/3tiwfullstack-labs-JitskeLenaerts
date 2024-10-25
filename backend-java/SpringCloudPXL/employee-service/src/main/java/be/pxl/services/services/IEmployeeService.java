package be.pxl.services.services;


import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<EmployeeResponse> getAllEmployees();

    void addEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse getEmployee(Long id);

    List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId);

    List<EmployeeResponse> getEmployeesByOrganizationId(Long organizationId);
}
