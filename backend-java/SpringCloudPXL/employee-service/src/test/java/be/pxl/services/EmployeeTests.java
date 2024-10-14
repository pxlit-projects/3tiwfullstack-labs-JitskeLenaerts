package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
import be.pxl.services.services.EmployeeService;
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
public class EmployeeTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer<?> sqlContainer =
            new MySQLContainer<>("mysql:8.0.39");

    @Autowired
    private EmployeeService employeeService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        Employee employee1 = Employee.builder()
                .age(35)
                .name("ds")
                .position("student")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        assertEquals(2, employeeRepository.findAll().size());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();
        employeeRepository.save(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.age").value(24))
                .andExpect(jsonPath("$.position").value("student"));

        assertEquals(1, employeeRepository.findAll().size());
    }
}
