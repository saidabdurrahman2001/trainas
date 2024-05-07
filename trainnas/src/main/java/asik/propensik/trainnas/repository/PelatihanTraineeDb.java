package asik.propensik.trainnas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.api.services.drive.model.User;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.PelatihanTrainee;

@Repository
public interface PelatihanTraineeDb extends JpaRepository<PelatihanTrainee,Long>{
    List<PelatihanTrainee> findByIdPelatihan(Pelatihan idPelatihan);
    List<PelatihanTrainee> findById(User id);
    
}