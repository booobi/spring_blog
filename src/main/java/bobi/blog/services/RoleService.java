package bobi.blog.services;

import bobi.blog.entities.Role;

import java.util.List;

public interface RoleService {
    Role getUserRole();

    Role getRoleById(Integer id);

    List<Role> getAllRoles();
}
