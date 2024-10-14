package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
import be.pxl.services.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Container
    private static MySQLContainer<?> sqlContainer =
            new MySQLContainer<>("mysql:8.0.39");

    @Autowired
    private DepartmentService departmentService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Department department = Department.builder()
                .position("student")
                .name("Jan")
                .build();

        Department department2 = Department.builder()
                .position("student")
                .name("Jan")
                .build();

        departmentRepository.save(department);
        departmentRepository.save(department2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        assertEquals(2, departmentRepository.findAll().size());
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = Department.builder()
                .position("student")
                .name("Jan")
                .build();

        departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + department.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department.getId()))
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.position").value("student"));

        assertEquals(1, departmentRepository.findAll().size());
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = Department.builder()
                .position("student")
                .name("Jan")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentString))
                .andExpect(status().isCreated());

        assertEquals(1, departmentRepository.findAll().size());
    }
}
