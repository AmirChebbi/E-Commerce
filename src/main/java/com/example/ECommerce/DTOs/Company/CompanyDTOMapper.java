package com.example.ECommerce.DTOs.Company;

import com.example.ECommerce.DAOs.Company.Company;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CompanyDTOMapper implements Function<Company, CompanyDTO> {

    @Override
    public CompanyDTO apply(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getLabel()
        );
    }
}
