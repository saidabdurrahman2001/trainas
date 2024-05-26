package asik.propensik.trainnas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asik.propensik.trainnas.dto.PelatihanMapper;
import asik.propensik.trainnas.dto.request.DaftarPelatihanDTO;
import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.PendaftaranService;
import asik.propensik.trainnas.service.UserServiceImpl;

@Controller
public class PelatihanController {
    @Autowired
    PelatihanMapper pelatihanMapper;

    @Autowired
    PelatihanService pelatihanService;

    @Autowired
    PendaftaranService pendaftaranService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/")
    public String hello(Model model) {
        List<UserModel> allUsers = userService.getAllUser();
        List<Pelatihan> listAllPelatihan = pelatihanService.getAllPelatihan();
        List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();
        System.out.println(listPelatihan);

        model.addAttribute("jumlahPengguna", allUsers.size());
        model.addAttribute("jumlahPelatihan", listPelatihan.size());
        model.addAttribute("listPelatihan", listPelatihan);
        return "home";
    }

    // @RequestMapping("/login")
    // public String login() {
    // return "login";
    // }

    @GetMapping("/pelatihan/add")
    public String formAddPelatihan(Model model) {
        return "trainer/form-create-pelatihan-rev";
    }

    @PostMapping("/pelatihan/add")
    public String formAddPelatihan(
            @RequestParam("namaPelatihan") String namaPelatihan,
            @RequestParam("tanggalMulai") String tanggalMulai,
            @RequestParam("deskripsi") String deskripsi,
            @RequestParam("tanggalAkhir") String tanggalAkhir,
            @RequestParam("tipe") String tipe,
            @RequestParam("waktuMulai") String waktuMulai,
            @RequestParam("penyelenggaraan") String penyelenggaraan,
            @RequestParam("locationInput") String locationInput,
            @RequestParam("narahubung") String narahubung,
            @RequestParam("batasRegistrasi") String batasRegistrasi,
            Model model) {

        // Buat objek Pelatihan
        Pelatihan pelatihan = new Pelatihan();

        // Set nilai-niilai dari form ke objek Pelatihan
        pelatihan.setNamaPelatihan(namaPelatihan);
        pelatihan.setDeskripsi(deskripsi);
        pelatihan.setTipe(tipe);
        pelatihan.setPenyelenggaraan(penyelenggaraan);
        pelatihan.setTempat(locationInput);
        pelatihan.setNarahubung(narahubung);
        // Parsing tanggalMulai dan tanggalAkhir menjadi objek Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tanggalMulaiDate = sdf.parse(tanggalMulai);
            pelatihan.setTanggal(tanggalMulaiDate);

            Date tanggalAkhirDate = sdf.parse(tanggalAkhir);
            pelatihan.setTanggalAkhir(tanggalAkhirDate);

            Date batasRegistrasiDate = sdf.parse(batasRegistrasi);
            pelatihan.setBatasRegistrasi(batasRegistrasiDate);

            LocalTime waktuMulaiTime = LocalTime.parse(waktuMulai);
            pelatihan.setWaktuMulai(waktuMulaiTime);
            // Set tanggalAkhir sesuai kebutuhan aplikasi Anda
            // Contoh: jika tanggalAkhir adalah tanggal selesai pelatihan, Anda bisa
            // mengaturnya di sini
            // pelatihan.setTanggalAkhir(tanggalAkhirDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pelatihanService.addPelatihan(pelatihan);

        // Kemudian, Anda dapat mengembalikan halaman yang sesuai
        return "trainer/succes-create-pelatihan";
    }

    // @PostMapping("/pelatihan/add")
    // public String formAddPelatihan(@ModelAttribute CreatePelatihanRequestDTO
    // pelatihanDTO, Model model) {
    // System.out.println("masuk post pelatihan add");
    // var pelatihan =
    // pelatihanMapper.createPelatihanRequestDTOToPelatihan(pelatihanDTO);
    // pelatihanService.addPelatihan(pelatihan);
    // return "trainer/succes-create-pelatihan";
    // }

    @GetMapping("pelatihan/viewall")
    public String listLatihan(Model model) {

        List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";

    }

    @GetMapping("pelatihan/viewall-trainer")
    public String listLatihanTrainer(Model model) {
        System.out.println("masuk viewall trainer");
        List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("pelatihan/viewall-trainer-manager")
    public String listLatihanTrainerManager(Model model) {

        List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "manager/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/daftar")
    public String formDaftarPelatihan(Model model, @RequestParam("id") Long pelatihanId) {
        Pelatihan pelatihan = pelatihanService.getPelatihanById(pelatihanId);
        model.addAttribute("pelatihan", pelatihan);
        model.addAttribute("daftarDTO", new DaftarPelatihanDTO());
        return "trainee/trainee-daftar-pelatihan";
    }

    // @PostMapping("/pelatihan/daftar")
    // public String daftarPelatihan(@ModelAttribute DaftarPelatihanDTO daftarDTO,
    // @RequestParam("id") Long pelatihanId) {
    // // Logika untuk menyimpan data pendaftaran
    // System.out.println(pelatihanId + "/n ini daftar DTO");
    // System.out.println(daftarDTO.getNamaLengkap() + "/n ini nama lengkap");

    // pendaftaranService.daftarPelatihan(pelatihanId, daftarDTO);
    // return "trainee/succes-mendaftar-pelatihan";
    // }
    // @PostMapping("/pelatihan/daftar/{id}")
    // public String daftarPelatihan(@PathVariable Long pelatihanId) {
    // System.out.println("MASUK POST DAFTAR PELATIHAN");
    // // Logika untuk menyimpan data pendaftaran
    // System.out.println(pelatihanId + "/n ini daftar DTO");
    // // System.out.println(daftarDTO.getNamaLengkap() + "/n ini nama lengkap");

    // String email = "said.abdurrahman@ui.ac.id";
    // UserModel user = userService.getUserByEmail(email);

    // pendaftaranService.daftarPelatihan(pelatihanId, user);
    // return "trainee/succes-mendaftar-pelatihan";
    // }
    @PostMapping("/pelatihan/daftar/{id}")
    public String daftarPelatihan(@PathVariable Long id) {
        System.out.println("MASUK POST DAFTAR PELATIHAN");
        // Logika untuk menyimpan data pendaftaran
        System.out.println(id + "/n ini daftar DTO");
        // System.out.println(daftarDTO.getNamaLengkap() + "/n ini nama lengkap");

        // String email = "admin2@gmail.com";
        // UserModel user = userService.getUserByEmail(email);

        UserModel user = userService.yangSedangLogin();

        pendaftaranService.daftarPelatihan(id, user);
        return "trainee/succes-mendaftar-pelatihan";
    }

    @GetMapping("/pelatihan/searchTrainee")
    public String searchPelatihan(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pelatihan> listPelatihan;
        System.out.println("masuk search trainee 1");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            System.out.println("masuk search trainee 2");
            // Ambil data pelatihan yang sudah di-approve dan sesuai dengan kriteria
            // pencarian
            listPelatihan = pelatihanService.searchApprovedPelatihanByTitle(searchQuery);
            System.out.println(listPelatihan);
            System.out.println("sampe final destination");
        } else {
            System.out.println("masuk search trainee 3");
            // Ambil semua data pelatihan yang sudah di-approve
            listPelatihan = pelatihanService.getAllApprovedPelatihan();
        }
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/searchDaftarSaya")
    public String searchPelatihanDaftarSaya(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pendaftaran> listPendaftaran;
        UserModel user = userService.yangSedangLogin();
        String email = user.getEmail();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            listPendaftaran = pendaftaranService.searchPelatihanByEmailAndNama(email, searchQuery);
        } else {
            listPendaftaran = pendaftaranService.getPelatihanByEmail(email);
        }
        model.addAttribute("listPendaftaran", listPendaftaran);

        return "trainee/daftarPelatihanSaya";
    }

    @GetMapping("/pelatihan/searchTrainer")
    public String searchTrainer(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pelatihan> listPelatihan;
        listPelatihan = pelatihanService.searchPelatihanByJudul(searchQuery);
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    // @GetMapping("/pelatihan/daftarPelatihanSaya")
    // public String daftarPelatihanSaya(Model model) {
    // // Dapatkan ID pelatihan yang unik dari pendaftaran
    // List<Long> pelatihanIds = pendaftaranService.findDistinctPelatihanId(); //
    // mesti implement kalo udh ada user

    // // Gunakan ID tersebut untuk mendapatkan detail pelatihan
    // List<Pelatihan> pelatihanList =
    // pelatihanService.findPelatihanByPendaftaranIds(pelatihanIds);

    // // Tambahkan daftar pelatihan ke model untuk ditampilkan di view
    // model.addAttribute("pelatihanList", pelatihanList);

    // // Nama file HTML (tanpa ekstensi .html) di dalam folder
    // // src/main/resources/templates
    // return "daftarPelatihanSaya";
    // }

    @GetMapping("/pelatihan/daftarPelatihanSaya")
    public String daftarPelatihanSaya(Model model) {
        UserModel user = userService.yangSedangLogin();
        String email = user.getEmail();
        List<Pendaftaran> listPendaftaran = pendaftaranService.getPelatihanByEmail(email);
        model.addAttribute("listPendaftaran", listPendaftaran);
        System.out.println("Masuk ke daftarPelatihanSaya");
        System.out.println(listPendaftaran.get(0).getPelatihan().getPenyelenggaraan());
        System.out.println(listPendaftaran.get(0).getPelatihan().getTipe());

        return "trainee/daftarPelatihanSaya";
    }

    @PostMapping("/pelatihan/cancel/{id}")
    public String cancelPendaftaran(@PathVariable("id") String idPelatihan) {
        System.out.println("masuk cancel");
        Long idPelatihan2 = Long.parseLong(idPelatihan);
        Pelatihan pelatihan = pelatihanService.getPelatihanById(idPelatihan2);
        UserModel user = userService.yangSedangLogin();

        // Menghapus pendaftaran berdasarkan ID pendaftaran
        pendaftaranService.cancelPendaftaran(user, pelatihan);
        return "trainee/success-batal-pendaftaran";
        // return "redirect:/pelatihan/daftarPelatihanSaya";
    }

    @PostMapping("/pelatihan/reject")
    public String rejectPelatihan(@RequestParam("idPelatihan") Long idPelatihan,
            @RequestParam("komentar") String komentar) {
        System.out.println(idPelatihan);
        System.out.println(komentar);
        pelatihanService.rejectPelatihan(idPelatihan, komentar);
        return "redirect:/pelatihan/viewall-trainer-manager"; // Sesuaikan dengan halaman yang diinginkan setelah
                                                              // penolakan
    }

    @PostMapping("/pelatihan/approve")
    public String approvePelatihan(@RequestParam("id") Long idPelatihan) {
        // Lakukan logika untuk mengubah statusApproval menjadi approved
        pelatihanService.approvePelatihan(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer-manager";
    }

    // please make controller for detail pelatihan
    // @GetMapping("/pelatihan/detail")
    // public String detailPelatihan(Model model, @RequestParam("id") Long
    // idPelatihan) {
    // var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
    // model.addAttribute("pelatihan", pelatihan);
    // // Lakukan logika untuk mengambil detail pelatihan berdasarkan ID
    // return "trainee/detailPelatihan"; // Sesuaikan dengan halaman yang diinginkan
    // }

    @GetMapping("/pelatihan/detail")
    public String detailPelatihan(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        var user = userService.yangSedangLogin();
        var udahDaftar = pendaftaranService.udahDaftar(user, pelatihan);
        model.addAttribute("pelatihan", pelatihan);
        model.addAttribute("udahDaftar", udahDaftar);
        // Lakukan logika untuk mengambil detail pelatihan berdasarkan ID
        return "trainee/detailPelatihanRev"; // Sesuaikan dengan halaman yang diinginkan
    }

    @GetMapping("/pelatihan/detail-trainer")
    public String detailPelatihanTrainer(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        model.addAttribute("pelatihan", pelatihan);
        return "trainer/detailPelatihan"; // Sesuaikan dengan halaman yang diinginkan
    }

    @GetMapping("/pelatihan/update")
    public String formUpdatePelatihan(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        System.out.println("ini adalah pelatihan" + pelatihan.getNamaPelatihan());
        model.addAttribute("pelatihan", pelatihan);
        return "trainer/form-update-pelatihan-rev";
    }

    @PostMapping("/pelatihan/update")
    public String updatePelatihan(Model model, @RequestParam("id") Long id,
            @RequestParam("namaPelatihan") String namaPelatihan,
            @RequestParam("tanggalMulai") String tanggalMulai,
            @RequestParam("deskripsi") String deskripsi,
            @RequestParam("tanggalAkhir") String tanggalAkhir,
            @RequestParam("batasRegistrasi") String batasRegistrasi,
            @RequestParam("tipe") String tipe,
            @RequestParam("waktuMulai") String waktuMulai,
            @RequestParam("penyelenggaraan") String penyelenggaraan,
            @RequestParam("locationInput") String locationInput,
            @RequestParam("narahubung") String narahubung) {

        // Dapatkan pelatihan yang ingin diperbarui dari database
        Pelatihan pelatihanToUpdate = pelatihanService.getPelatihanById(id);

        // Perbarui nilai atribut pelatihan
        pelatihanToUpdate.setNamaPelatihan(namaPelatihan);
        pelatihanToUpdate.setDeskripsi(deskripsi);
        pelatihanToUpdate.setTipe(tipe);
        pelatihanToUpdate.setPenyelenggaraan(penyelenggaraan);
        pelatihanToUpdate.setTempat(locationInput);
        pelatihanToUpdate.setNarahubung(narahubung);
        pelatihanToUpdate.setStatusApproval(1);

        // Parsing tanggalMulai dan tanggalAkhir menjadi objek Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tanggalMulaiDate = sdf.parse(tanggalMulai);
            pelatihanToUpdate.setTanggal(tanggalMulaiDate);

            Date tanggalAkhirDate = sdf.parse(tanggalAkhir);
            pelatihanToUpdate.setTanggalAkhir(tanggalAkhirDate);

            Date batasRegistrasiDate = sdf.parse(batasRegistrasi);
            pelatihanToUpdate.setBatasRegistrasi(batasRegistrasiDate);

            LocalTime waktuMulaiTime = LocalTime.parse(waktuMulai);
            pelatihanToUpdate.setWaktuMulai(waktuMulaiTime);
            // Set tanggalAkhir sesuai kebutuhan aplikasi Anda
            // Contoh: jika tanggalAkhir adalah tanggal selesai pelatihan, Anda bisa
            // mengaturnya di sini
            // pelatihanToUpdate.setTanggalAkhir(tanggalAkhirDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Anda dapat menambahkan validasi atau logika lainnya di sini

        // Simpan perubahan ke dalam database
        pelatihanService.addPelatihan(pelatihanToUpdate);

        // Redirect ke halaman yang sesuai, misalnya halaman detail pelatihan
        return "redirect:/pelatihan/detail?id=" + id;
    }

    // @PostMapping("/pelatihan/update")
    // public String updatePelatihan(Model model, @RequestParam("id") Long
    // idPelatihan,
    // @RequestParam("namaPelatihan") String namaPelatihan,
    // @RequestParam("tipe") String tipe,
    // @RequestParam("tempat") String tempat,
    // @RequestParam("deskripsi") String deskripsi,
    // @RequestParam("narahubung") String narahubung,
    // @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("tanggal") Date
    // tanggal) {

    // var pelatihanDTO = new UpdatePelatihanRequestDTO();
    // pelatihanDTO.setIdPelatihan(idPelatihan);
    // pelatihanDTO.setNamaPelatihan(namaPelatihan);
    // pelatihanDTO.setTipe(tipe);
    // pelatihanDTO.setTempat(tempat);
    // pelatihanDTO.setDeskripsi(deskripsi);
    // pelatihanDTO.setNarahubung(narahubung);
    // pelatihanDTO.setTanggal(new java.sql.Date(tanggal.getTime()));

    // System.out.println(pelatihanDTO.getIdPelatihan() + "ini adalah id
    // pelatihan");

    // var pelatihanFromDto =
    // pelatihanMapper.updatePelatihanRequestDTOToPelatihan(pelatihanDTO);
    // System.out.println(pelatihanFromDto.getNamaPelatihan() + "ini adalah nama
    // pelatihan");
    // System.out.println(pelatihanFromDto.getIdPelatihan() + "ini adalah
    // idpelatihan");

    // var pelatihan = pelatihanService.updatePelatihan(pelatihanFromDto);
    // model.addAttribute("pelatihan", pelatihan);

    // return "redirect:/pelatihan/viewall-trainer";
    // }

    @GetMapping("/pelatihan/delete/{id}")
    public String deletePelatihan(@PathVariable("id") Long idPelatihan) {
        pelatihanService.deletePelatihanReq(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer";
    }

    @PostMapping("/pelatihan/delete")
    public String deletePelatihan(@RequestParam("id") Long idPelatihan, Model model) {
        pelatihanService.deletePelatihan(idPelatihan);
        return "trainer/success-delete-pelatihan";
    }

    @PostMapping("/pelatihan/delete/{id}")
    public String deletePelatihanTrainer(@PathVariable("id") Long idPelatihan, Model model) {
        pelatihanService.deletePelatihanReq(idPelatihan);
        return "trainer/success-requestdelete-pelatihan";
    }

    @PostMapping("/pelatihan/done")
    public String donePelatihan(@RequestParam("id") Long idPelatihan) {
        pelatihanService.donePelatihan(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer-manager";
    }

    @GetMapping("/pelatihan/taka")
    public String takaPelatihan(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihan();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/taba")
    public String tabaPelatihan(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihan();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/takaSaya")
    public String takaPelatihanSaya(Model model) {
        List<Pendaftaran> listPendaftaran = pendaftaranService.getTakaPelatihanSaya("A");
        System.out.println(listPendaftaran.size() + "ini adalah size");
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    @GetMapping("/pelatihan/tabaSaya")
    public String tabaPelatihanSaya(Model model) {
        List<Pendaftaran> listPendaftaran = pendaftaranService.getTabaPelatihanSaya("A");
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    // @GetMapping("/pelatihan/daftarPelatihanSaya")
    // public String daftarPelatihanSaya(Model model) {
    // List<Pendaftaran> listPendaftaran =
    // pendaftaranService.getPelatihanByAsalSekolah("A");
    // model.addAttribute("listPendaftaran", listPendaftaran);
    // return "trainee/daftarPelatihanSaya";
    // }

    @GetMapping("/pelatihan/takaTrainer")
    public String takaPelatihanTrainer(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihanTrainer();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/tabaTrainer")
    public String tabaPelatihanTrainer(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihanTrainer();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    // @GetMapping("pelatihan/viewall")
    // public String listLatihan(Model model) {

    // List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();

    // model.addAttribute("listPelatihan", listPelatihan);
    // return "trainee/trainee-viewall-pelatihan";

    // }
    @GetMapping("/pelatihan/filterPelatihanTrainee")
    public String filterPelatihanTrainee(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        if ("All".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaka".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaba".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        }
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/filterPelatihanTrainer")
    public String filterPelatihanTrainer(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter dashboard");
        if ("All".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaka".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihanTrainer();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaba".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihanTrainer();
            model.addAttribute("listPelatihan", listPelatihan);
        }
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/filterDaftarPelatihanSaya")
    public String filterDaftarPelatihanSaya(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        UserModel user = userService.yangSedangLogin();
        String email = user.getEmail();
        if ("All".equals(sortType)) {
            var listPendaftaran = pendaftaranService.getPelatihanByEmail(email);
            model.addAttribute("listPendaftaran", listPendaftaran);
        } else if ("Gernastastaka".equals(sortType)) {
            var listPendaftaran = pendaftaranService.getTakaPelatihanSaya(email);
            model.addAttribute("listPendaftaran", listPendaftaran);
        } else if ("Gernastastaba".equals(sortType)) {
            var listPendaftaran = pendaftaranService.getTabaPelatihanSaya(email);
            model.addAttribute("listPendaftaran", listPendaftaran);
        }
        return "trainee/daftarPelatihanSaya";
    }
}
