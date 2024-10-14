package be.pxl.services.repository;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findDepartmentByOrganizationId(Long organizationId);
}
