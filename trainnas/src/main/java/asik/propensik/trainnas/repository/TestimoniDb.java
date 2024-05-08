package asik.propensik.trainnas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.model.UserModel;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TestimoniDb extends JpaRepository<Testimoni, Long> {
    public List<Testimoni> findByUser(UserModel userModel);
    // List<Testimoni> findByNamaContainingIgnoreCase(String nama);

}
