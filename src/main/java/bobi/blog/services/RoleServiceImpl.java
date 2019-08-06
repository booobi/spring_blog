package bobi.blog.services;

import bobi.blog.entities.Role;
import bobi.blog.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private final static String NORMAL_USER_ROLE = "ROLE_USER";
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getUserRole() {
        return this.roleRepository.findByName(NORMAL_USER_ROLE);
    }
}
