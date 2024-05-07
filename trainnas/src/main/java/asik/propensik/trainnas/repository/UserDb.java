package asik.propensik.trainnas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.UserModel;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserDb extends JpaRepository<UserModel, UUID> {
    // List<UserModel> findByUsername(String name);

    List<UserModel> findByNameContainingIgnoreCase(String name);

    // List<UserModel> findByRole(String role);

    UserModel findByEmail(String email);

    UserModel findByUsername(String username);

    // UserModel findById(UUID id);
}
