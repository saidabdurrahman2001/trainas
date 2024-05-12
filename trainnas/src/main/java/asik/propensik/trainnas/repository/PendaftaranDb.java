package asik.propensik.trainnas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.model.Pelatihan;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PendaftaranDb extends JpaRepository<Pendaftaran, Long> {
        Optional<Pendaftaran> findById(Long idPelatihan);

        @Query("SELECT DISTINCT p.pelatihan.id FROM Pendaftaran p")
        List<Long> findDistinctPelatihanId();

        // List<Pendaftaran> findByAsalSekolah(String asalSekolah);

        List<Pendaftaran> findByUser_Email(String email);

        // List<Pendaftaran>
        // findByAsalSekolahAndPelatihanNamaPelatihanContainingIgnoreCase(String
        // asalSekolah,

        // String namaPelatihan);
        List<Pendaftaran> findByUser_EmailAndPelatihan_NamaPelatihanContainingIgnoreCase(String email,
                        String namaPelatihan);

        // List<Pendaftaran> findByAsalSekolahAndPelatihan_Tipe(String asalSekolah,
        // String tipe);

        List<Pendaftaran> findByPelatihan_TipeAndUser_Email(String tipe, String email);

        List<Pendaftaran> findByPelatihan_IdPelatihan(Long id);

        List<Pendaftaran> findByPelatihan_Tipe(String tipe);

        long countByPelatihan_Tipe(String tipe);

        // Method untuk mengambil jumlah pendaftar per pelatihan
        @Query("SELECT p.pelatihan.namaPelatihan AS pelatihan, COUNT(p) AS jumlah " +
                        "FROM Pendaftaran p " +
                        "GROUP BY p.pelatihan.namaPelatihan")
        List<Object[]> countPendaftarPerPelatihan();

        @Query("SELECT MONTH(p.waktuPembuatan) AS bulan, COUNT(p) AS jumlah " +
                        "FROM Pendaftaran p " +
                        "GROUP BY MONTH(p.waktuPembuatan)")
        List<Object[]> countPendaftarPerBulan();

        @Query("SELECT p.pelatihan.idPelatihan, COUNT(p) AS jumlahPendaftar " +
                        "FROM Pendaftaran p " +
                        "GROUP BY p.pelatihan.idPelatihan " +
                        "ORDER BY jumlahPendaftar DESC")
        List<Long> findTop3PelatihanByJumlahPendaftar();

        List<Pendaftaran> findByWaktuPembuatanBetween(LocalDateTime startTime, LocalDateTime endTime);

        @Query("SELECT p.pelatihan FROM Pendaftaran p WHERE p.user = :user")
        List<Pelatihan> findPelatihanByUser(UserModel user);

        Boolean existsByUserAndPelatihan(UserModel user, Pelatihan pelatihan);

        Pendaftaran findByUserAndPelatihan(UserModel user, Pelatihan pelatihan);
}
