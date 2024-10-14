package be.pxl.services.services;


import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import be.pxl.services.exception.*;

@Service
@RequiredArgsConstructor
public class OrganizationService  implements IOrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse findOrganizationById(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new NotFoundException("No organization with id [" + organizationId + "]"));
        return new OrganizationResponse(organization.getName(), organization.getAddress(), organization.getEmployees(), organization.getDepartments());
    }

    @Override
    public OrganizationResponse findByOrganizationIdWithDepartments(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new NotFoundException("No organization with id [" + organizationId + "]"));
        return new OrganizationResponse(organization.getName(), organization.getAddress(), organization.getEmployees(), organization.getDepartments());

    }

    @Override
    public OrganizationResponse findByOrganizationIdWithDepartmentsAndEmployees(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new NotFoundException("No organization with id [" + organizationId + "]"));
        return new OrganizationResponse(organization.getName(), organization.getAddress(), organization.getEmployees(), organization.getDepartments());

    }

    @Override
    public OrganizationResponse findByOrganizationIdWithEmployees(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new NotFoundException("No organization with id [" + organizationId + "]"));
        return new OrganizationResponse(organization.getName(), organization.getAddress(), organization.getEmployees(), null);

    }
}
