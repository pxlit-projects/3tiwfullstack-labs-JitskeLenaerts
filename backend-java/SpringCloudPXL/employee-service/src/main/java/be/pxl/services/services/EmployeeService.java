package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    @Override
    public Optional<Employee> findById(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee;
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    @Override
    public List<EmployeeResponse> findByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganization(Long organizationId) {
        List<Employee> employees = employeeRepository.findByOrganizationId(organizationId);
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }


    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .build();
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .position(employeeRequest.getPosition())
                .organizationId(employeeRequest.getOrganizationId())
                .departmentId(employeeRequest.getDepartmentId())
                .build();
        employeeRepository.save(employee);
        NotificationRequest notificationRequest = NotificationRequest.builder().message("Employee Created").sender("Tom").build();
        notificationClient.sendNotification(notificationRequest);
    }



}
