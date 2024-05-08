package asik.propensik.trainnas.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.dto.request.DaftarPelatihanDTO;
import asik.propensik.trainnas.dto.request.UpdatePelatihanRequestDTO;
import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.repository.PelatihanDb;
import asik.propensik.trainnas.repository.PendaftaranDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class PendaftaranService {
    @Autowired
    PendaftaranDb pendaftaranDb;

    @Autowired
    PelatihanService pelatihanService;

    @Autowired
    private EntityManager entityManager;

    // public void daftarPelatihan(Long id, DaftarPelatihanDTO daftarDTO) {
    // Pelatihan pelatihan = pelatihanService.getPelatihanById(id);
    // Pendaftaran pendaftaran = new Pendaftaran();
    // pendaftaran.setPelatihan(pelatihan);
    // pendaftaran.setNamaLengkap(daftarDTO.getNamaLengkap());
    // pendaftaran.setAsalSekolah(daftarDTO.getAsalSekolah());
    // pendaftaran.setEmail(daftarDTO.getEmail());
    // pendaftaran.setNoTelepon(daftarDTO.getNoTelepon());
    // pendaftaranDb.save(pendaftaran);

    // }
    public void daftarPelatihan(Long idPelatihan, UserModel user) {
        Pelatihan pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        pelatihan.setJumlahPendaftar(pelatihan.getJumlahPendaftar() + 1); // Menambah jumlah pendaftar pada pelatihan
        Pendaftaran pendaftaran = new Pendaftaran();
        pendaftaran.setPelatihan(pelatihan);
        pendaftaran.setUser(user);
        pendaftaranDb.save(pendaftaran);

    }

    public List<Long> findDistinctPelatihanId() {
        return pendaftaranDb.findDistinctPelatihanId();
    }

    public List<Pelatihan> getPelatihanByUser(UserModel user) {
        return pendaftaranDb.findPelatihanByUser(user);
    }

    // public List<Pendaftaran> getPelatihanByAsalSekolah(String asalSekolah) {
    // return pendaftaranDb.findByAsalSekolah(asalSekolah);
    // }

    public List<Pendaftaran> getPelatihanByEmail(String email) {
        return pendaftaranDb.findByUser_Email(email);
    }

    // public List<Pendaftaran> getTakaPelatihanSaya(String asalSekolah) {
    // return pendaftaranDb.findByAsalSekolahAndPelatihan_Tipe(asalSekolah,
    // "Gernastastaka");
    // }
    public List<Pendaftaran> getTakaPelatihanSaya(String email) {
        return pendaftaranDb.findByPelatihan_TipeAndUser_Email(
                "Gernastastaka", email);
    }

    // public List<Pendaftaran> getTabaPelatihanSaya(String asalSekolah) {
    // return pendaftaranDb.findByAsalSekolahAndPelatihan_Tipe(asalSekolah,
    // "Gernastastaba");
    // }
    public List<Pendaftaran> getTabaPelatihanSaya(String email) {
        return pendaftaranDb.findByPelatihan_TipeAndUser_Email(
                "Gernastastaba", email);
    }

    public void cancelPendaftaran(Long idPendaftaran) {
        // Menghapus pendaftaran berdasarkan ID pendaftaran
        pendaftaranDb.deleteById(idPendaftaran);
    }

    // public List<Pendaftaran> searchPelatihanByAsalSekolahAndNama(String
    // asalSekolah, String namaPelatihan) {
    // List<Pendaftaran> listPendaftaran = pendaftaranDb
    // .findByAsalSekolahAndPelatihanNamaPelatihanContainingIgnoreCase(asalSekolah,
    // namaPelatihan);
    // return listPendaftaran;
    // }

    public List<Pendaftaran> searchPelatihanByEmailAndNama(String email, String namaPelatihan) {
        List<Pendaftaran> listPendaftaran = pendaftaranDb
                .findByUser_EmailAndPelatihan_NamaPelatihanContainingIgnoreCase(email,
                        namaPelatihan);
        return listPendaftaran;
    }

    public long countPendaftaranByTipePelatihan(String tipe) {
        // Menghitung jumlah pendaftaran berdasarkan tipe pelatihan
        return pendaftaranDb.countByPelatihan_Tipe(tipe);
    }

    public List<Map<String, Object>> countPendaftarPerPelatihan() {
        List<Object[]> results = pendaftaranDb.countPendaftarPerPelatihan();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("pelatihan", result[0]);
            data.put("jumlah", result[1]);
            resultList.add(data);
        }

        return resultList;
    }

    public List<Map<String, Object>> countPendaftarPerBulan() {
        List<Object[]> results = pendaftaranDb.countPendaftarPerBulan();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            int bulan = (int) result[0];
            String namaBulan = getNamaBulan(bulan); // Mendapatkan nama bulan berdasarkan nomor bulan
            long jumlah = (long) result[1];
            data.put("nama bulan", namaBulan);
            data.put("jumlah", jumlah);
            resultList.add(data);
        }

        return resultList;
    }

    // Method untuk mendapatkan nama bulan berdasarkan nomor bulan
    private String getNamaBulan(int bulan) {
        String[] namaBulan = { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September",
                "Oktober", "November", "Desember" };
        return namaBulan[bulan - 1];
    }

    public List<Pelatihan> findTop3PelatihanByJumlahPendaftar() {
        List<Long> top3PelatihanId = pendaftaranDb.findTop3PelatihanByJumlahPendaftar();
        List<Pelatihan> top3Pelatihan = new ArrayList<>();
        for (Long id : top3PelatihanId) {
            Pelatihan pelatihan = pelatihanService.getPelatihanById(id);
            top3Pelatihan.add(pelatihan);
        }
        return top3Pelatihan;
    }

    public List<Map<String, Object>> getJumlahPendaftaranPerBulan() {
        // Ambil waktu saat ini
        LocalDateTime currentTime = LocalDateTime.now();

        // Hitung waktu 3 bulan yang lalu
        LocalDateTime threeMonthsAgo = currentTime.minusMonths(3);

        // Ambil data pendaftaran dalam kurun waktu 3 bulan terakhir dari repository
        List<Pendaftaran> pendaftarans = pendaftaranDb.findByWaktuPembuatanBetween(threeMonthsAgo, currentTime);

        // Buat map untuk menyimpan jumlah pendaftaran per bulan
        Map<String, Integer> jumlahPendaftaranPerBulan = new HashMap<>();

        // Iterasi melalui data pendaftaran dan hitung jumlah pendaftaran per bulan
        for (Pendaftaran pendaftaran : pendaftarans) {
            String bulan = YearMonth.from(pendaftaran.getWaktuPembuatan()).toString();

            jumlahPendaftaranPerBulan.put(bulan, jumlahPendaftaranPerBulan.getOrDefault(bulan, 0) + 1);
        }

        // Buat list untuk menyimpan data jumlah pendaftaran per bulan
        List<Map<String, Object>> dataJumlahPendaftaran = new ArrayList<>();

        // Iterasi melalui map dan tambahkan data jumlah pendaftaran per bulan ke list
        for (Map.Entry<String, Integer> entry : jumlahPendaftaranPerBulan.entrySet()) {
            Map<String, Object> bulanData = new HashMap<>();
            bulanData.put("bulan", entry.getKey());
            bulanData.put("jumlahPendaftaran", entry.getValue());

            dataJumlahPendaftaran.add(bulanData);
        }

        return dataJumlahPendaftaran;
    }

}