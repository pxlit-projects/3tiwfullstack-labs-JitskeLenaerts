package be.pxl.services.controller;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.*;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity getEmployees() {
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> findByDepartment(@PathVariable Long departmentId) {
        return new ResponseEntity(employeeService.findByDepartment(departmentId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<EmployeeResponse>> findByOrganization(@PathVariable Long organizationId) {
        return new ResponseEntity(employeeService.findByOrganization(organizationId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
    }
}
