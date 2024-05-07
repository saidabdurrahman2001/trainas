package asik.propensik.trainnas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.model.Role;
import asik.propensik.trainnas.repository.RoleDb;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDb roleDb;

    @Override
    public Role findByRole(String role){
        return roleDb.findByRole(role).get();
    }

    @Override
    public List<Role> findAllRole(){
        return roleDb.findAll();
    }

}
