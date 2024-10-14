package be.pxl.services.service;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("No department id" + departmentId));
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();
    }

    @Override
    public List<DepartmentResponse> findByOrganizationWithEmployees(Long organizationId) {
        List<Department> departments = departmentRepository.findDepartmentByOrganizationId(organizationId);

        return departments.stream()
                .map(department -> mapToDepartmentResponseWithEmployees(department))
                .toList();
    }

    @Override
    public List<DepartmentResponse> findDepartmentByOrganizationWithEmployees(Long organizationId) {
        List<Department> departments = departmentRepository.findDepartmentByOrganizationId(organizationId).stream().toList();
        if (departments.isEmpty()) {
            throw new NotFoundException("No departments found for organization with id [" + organizationId + "]");
        }
        List<DepartmentResponse> departmentDTOS = new ArrayList<>();
        for (Department department : departments) {
            departmentDTOS.add(new DepartmentResponse(department.getOrganizationId(), department.getName(), department.getEmployees(), department.getPosition()));
        }
        return departmentDTOS;
    }

    private DepartmentResponse mapToDepartmentResponseWithEmployees(Department department) {
        return DepartmentResponse.builder()
                .name(department.getName())
                .organizationId(department.getOrganizationId())
                .employees(department.getEmployees())
                .build();
    }


    @Override
    public List<DepartmentResponse> findByOrganization(Long organizationId) {
        List<Department> departments = departmentRepository.findDepartmentByOrganizationId(organizationId);
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .organizationId(departmentRequest.getOrganizationId())
                .employees(departmentRequest.getEmployees())
                .build();
        departmentRepository.save(department);
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .name(department.getName())
                .organizationId(department.getOrganizationId())
                .employees(department.getEmployees())
                .build();
    }

}
