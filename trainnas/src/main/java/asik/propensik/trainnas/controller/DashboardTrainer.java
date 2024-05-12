package asik.propensik.trainnas.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.PendaftaranService;
import asik.propensik.trainnas.service.TestimoniService;
import asik.propensik.trainnas.service.UserServiceImpl;

import java.util.Comparator;

@Controller
@RequestMapping("/dashboard")
public class DashboardTrainer {

    @Autowired
    private PelatihanService pelatihanService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TestimoniService testimoniService;

    @Autowired
    private PendaftaranService pendaftaranService;

    @GetMapping("/trainee")
    public String traineeDashboard(Model model) {
        UserModel user = userService.yangSedangLogin();
        String namaUser = user.getName();
        List<Pelatihan> listPelatihan = pendaftaranService.getPelatihanByUser(user);
        System.out.println(listPelatihan.size());
        System.out.println("MASUK DASHBOARD TRAINEE");
        model.addAttribute("user", namaUser);
        model.addAttribute("listPelatihan", listPelatihan);

        List<Testimoni> testimoniList = testimoniService.findByUser(user);
        model.addAttribute("listTestimoni", testimoniList);


        return "dashboard-trainee";
    }

    @GetMapping("/testimoni")
    public String getAllTestimoniByLoggedInUser(Model model) {
        UserModel user = userService.yangSedangLogin();
        List<Testimoni> testimoniList = testimoniService.findByUser(user);
        model.addAttribute("testimoniList", testimoniList);
        return "testimoni-list";
    }

    

    @GetMapping("/trainer")
    public String trainerDashboard(Model model) {

        // Punya Iky
        // Ambil semua pelatihan yang dibuat oleh trainer
        List<Pelatihan> pelatihanTakaList = pelatihanService.getTakaPelatihanTrainer();
        model.addAttribute("pelatihanList", pelatihanTakaList);

        List<Pelatihan> pelatihanTabaList = pelatihanService.getTabaPelatihanTrainer();
        model.addAttribute("pelatihanList", pelatihanTabaList);

        List<Pelatihan> pelatihanList = pelatihanService.getAllApprovedPelatihan();
        model.addAttribute("pelatihanList", pelatihanList);

        // Punya Aman
        long jumlahGernastastaka = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaka");
        long jumlahGernastastaba = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaba");
        model.addAttribute("jumlahGernastastaka", jumlahGernastastaka);
        model.addAttribute("jumlahGernastastaba", jumlahGernastastaba);

        List<Pelatihan> top3 = pendaftaranService.findTop3PelatihanByJumlahPendaftar();
        int maxPelatihanToShow = top3.size();

        for (int i = 0; i < Math.min(top3.size(), maxPelatihanToShow); i++) {
            Pelatihan pelatihan = top3.get(i);
            model.addAttribute("pelatihan" + (i + 1), pelatihan.getNamaPelatihan());
            model.addAttribute("jumlah" + (i + 1), pelatihan.getJumlahPendaftar());
        }

        model.addAttribute("maxPelatihanToShow", maxPelatihanToShow);

        List<Map<String, Object>> dataJumlahPendaftaran = pendaftaranService.getJumlahPendaftaranPerBulan();
        // Urutkan dataJumlahPendaftaran berdasarkan bulan (dalam format LocalDate)
        dataJumlahPendaftaran.sort(Comparator.comparing(m -> {
            String bulanString = (String) m.get("bulan");
            YearMonth yearMonth = YearMonth.parse(bulanString, DateTimeFormatter.ofPattern("yyyy-MM"));
            return yearMonth;
        }));

        int size = dataJumlahPendaftaran.size();

        for (int i = 0; i < size; i++) {
            Map<String, Object> data = dataJumlahPendaftaran.get(i);
            System.out.println(data.get("bulan"));
            System.out.println(data.get("jumlahPendaftaran"));
            model.addAttribute("bulan" + (i + 1), data.get("bulan"));
            model.addAttribute("jumlahPendaftaran" + (i + 1), data.get("jumlahPendaftaran"));
        }
        model.addAttribute("size", size);

        System.out.println(dataJumlahPendaftaran);
        System.out.println("amanaman");

        // ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.registerModule(new JavaTimeModule());
        // try {
        // String top3Json = objectMapper.writeValueAsString(top3);
        // System.out.println("MASUK KENDRIK");
        // System.out.println(top3Json);
        // model.addAttribute("top3Json", top3Json);

        // } catch (JsonProcessingException e) {
        // e.printStackTrace();
        // }

        // ObjectMapper objectMapper = new ObjectMapper();
        // String top3Json = objectMapper.writeValueAsString(top3);
        // model.addAttribute("top3Json", top3Json);
        // model.addAttribute("top3", top3);
        // System.out.println("NYAMPE DASHBOARD CONTROLLER");
        // System.out.println(top3);
        // System.out.println(top3.get(0));
        // System.out.println(top3.get(0).getClass());
        // System.out.println(top3.get(0));
        // System.out.println(top3.get(0).getNamaPelatihan());
        // System.out.println(((Map<String, Object>) top3.get(0)).get("namaPelatihan"));
        // System.out.println("HOORAY");
        // System.out.println("jumlahGernastastaka: " + jumlahGernastastaka);
        // System.out.println("jumlahGernastastaba: " + jumlahGernastastaba);

        // var top3 = pendaftaranService.countPendaftarPerPelatihan();
        // System.out.println(top3);
        // System.out.println((String) top3.get(0).get("pelatihan"));
        // if (top3.size() > 2) {
        // model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
        // model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
        // model.addAttribute("pelatihan3", (String) top3.get(2).get("pelatihan"));
        // model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
        // model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));
        // model.addAttribute("jumlah3", (Long) top3.get(2).get("jumlah"));
        // } else {
        // model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
        // model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
        // model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
        // model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));

        // }
        // model.addAttribute("top3", top3);

        // var pendaftarPerBulan = pendaftaranService.countPendaftarPerBulan();

        // System.out.println(pendaftarPerBulan);
        // model.addAttribute("bulan1", (String) pendaftarPerBulan.get(0).get("nama
        // bulan"));
        // model.addAttribute("jumlah1", (Long) pendaftarPerBulan.get(0).get("jumlah"));

        return "dashboard-trainer";
    }

}

// @GetMapping("/trainer")
// public String trainerManagerDashboard(Model model) {
// // Ambil semua pelatihan yang membutuhkan persetujuan dari trainer manager
// List<Pelatihan> pelatihanList = pelatihanService.getAllApprovedPelatihan();
// model.addAttribute("pelatihanList", pelatihanList);
// return "trainer_manager_dashboard";
// }

// -------------------------

// @GetMapping("/trainer")
// public String trainerDashboard(Model model) {
// // Ambil semua pelatihan yang dibuat oleh trainer (tipe Gernastastaka)
// List<Pelatihan> takaPelatihanList =
// pelatihanService.getTakaPelatihanTrainer();
// model.addAttribute("takaPelatihanList", takaPelatihanList);

// // Ambil semua pelatihan yang dibuat oleh trainer (tipe Gernastastaba)
// List<Pelatihan> tabaPelatihanList =
// pelatihanService.getTabaPelatihanTrainer();
// model.addAttribute("tabaPelatihanList", tabaPelatihanList);

// // Ambil status dari masing-masing pelatihan
// for (Pelatihan pelatihan : takaPelatihanList) {
// // Lakukan pengecekan status dan simpan dalam atribut model
// String status = getStatusPelatihan(pelatihan);
// model.addAttribute("status_" + pelatihan.getIdPelatihan(), status);
// }

// for (Pelatihan pelatihan : tabaPelatihanList) {
// // Lakukan pengecekan status dan simpan dalam atribut model
// String status = getStatusPelatihan(pelatihan);
// model.addAttribute("status_" + pelatihan.getIdPelatihan(), status);
// }

// return "dashboard-trainer";
// }

// private String getStatusPelatihan(Pelatihan pelatihan) {
// if (pelatihan.getStatusApproval() == 2 || pelatihan.getStatusApproval() == 5)
// {
// return "Selesai";
// } else {
// return "Berjalan";
// }
// }

// }
