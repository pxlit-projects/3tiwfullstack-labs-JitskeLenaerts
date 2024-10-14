package be.pxl.services.controller;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity getAllDepartments() {
        return new ResponseEntity(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> findById(@PathVariable Long departmentId) {
        Department department=departmentService.findById(departmentId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DepartmentResponse>> findByOrganization(@PathVariable Long organizationId) {
        List<DepartmentResponse> organization = departmentService.findByOrganization(organizationId);
        if (organization.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(organization);
    }

//    @GetMapping("/organization/{organizationId}/with-employees")
//    public ResponseEntity<List<DepartmentResponse>> findByOrganizationWithEmployees(@PathVariable Long organizationId) {
//        List<DepartmentResponse> organizations = departmentService.findByOrganizationWithEmployees(organizationId);
//        if (organizations.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(organizations);
//    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.addDepartment(departmentRequest);
    }

}
