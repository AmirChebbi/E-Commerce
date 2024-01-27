package com.example.ECommerce.Services.Role;



import com.example.ECommerce.DAOs.Role.Role;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role fetchRoleByName(String roleName) {
        return roleRepository.fetchRoleByName(roleName).orElseThrow(
                ()-> new ResourceNotFoundException("The role with name : %s could not be found.")
        );
    }
}
