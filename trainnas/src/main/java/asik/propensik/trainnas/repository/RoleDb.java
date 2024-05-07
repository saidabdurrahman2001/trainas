package asik.propensik.trainnas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Role;


@Repository
public interface RoleDb extends JpaRepository<Role, UUID>{
    Optional<Role> findByRole(String role);
}