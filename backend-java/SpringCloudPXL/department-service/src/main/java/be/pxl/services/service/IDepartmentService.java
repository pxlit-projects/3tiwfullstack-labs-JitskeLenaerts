package be.pxl.services.service;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.*;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    Department findById(long departmentId);

    List<DepartmentResponse> getAllDepartments();

    List<DepartmentResponse> findByOrganizationWithEmployees(Long organizationId);

    List<DepartmentResponse> findDepartmentByOrganizationWithEmployees(Long organizationId);

    List<DepartmentResponse> findByOrganization(Long organizationId);

    void addDepartment(DepartmentRequest departmentRequest);
}
