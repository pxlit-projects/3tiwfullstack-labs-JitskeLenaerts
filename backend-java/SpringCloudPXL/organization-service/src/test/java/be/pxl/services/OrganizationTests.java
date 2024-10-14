package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import be.pxl.services.services.OrganizationService;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class OrganizationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Container
    private static MySQLContainer<?> sqlContainer =
            new MySQLContainer<>("mysql:8.0.39");

    @Autowired
    private OrganizationService organizationService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        organizationRepository.deleteAll();
    }

    @Test
    public void testGetOrganizationId() throws Exception {
        Organization organization = Organization.builder()
                .name("jan")
                .address("Steenbrugge")
                .build();

        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + organization.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jan"))
                .andExpect(jsonPath("$.address").value("Steenbrugge"));

        assertEquals(1, organizationRepository.findAll().size());
    }
}
