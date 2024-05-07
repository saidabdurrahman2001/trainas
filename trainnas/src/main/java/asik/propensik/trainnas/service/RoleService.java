package asik.propensik.trainnas.service;

import java.util.List;

import asik.propensik.trainnas.model.Role;

public interface RoleService {
    Role findByRole(String role);
    List<Role> findAllRole();
}
