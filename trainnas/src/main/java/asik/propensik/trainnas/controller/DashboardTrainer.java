package asik.propensik.trainnas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.PendaftaranService;

@Controller
@RequestMapping("/dashboard")
public class DashboardTrainer {
    
    @Autowired
    private PelatihanService pelatihanService;
    
    @Autowired
    private PendaftaranService pendaftaranService;
    
    @GetMapping("/trainer")
    public String trainerDashboard(Model model) {

        // //Punya Aman
        // long jumlahGernastastaka = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaka");
        // long jumlahGernastastaba = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaba");
        // model.addAttribute("jumlahGernastastaka", jumlahGernastastaka);
        // model.addAttribute("jumlahGernastastaba", jumlahGernastastaba);
        // System.out.println("jumlahGernastastaka: " + jumlahGernastastaka);
        // System.out.println("jumlahGernastastaba: " + jumlahGernastastaba);

        // var top3 = pendaftaranService.countPendaftarPerPelatihan();
        // System.out.println(top3);
        // System.out.println((String) top3.get(0).get("pelatihan"));
        // if (top3.size() > 2) {
        //     model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
        //     model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
        //     model.addAttribute("pelatihan3", (String) top3.get(2).get("pelatihan"));
        //     model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
        //     model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));
        //     model.addAttribute("jumlah3", (Long) top3.get(2).get("jumlah"));
        // } else {
        //     model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
        //     model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
        //     model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
        //     model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));

        // }
        // model.addAttribute("top3", top3);

        // var pendaftarPerBulan = pendaftaranService.countPendaftarPerBulan();

        // System.out.println(pendaftarPerBulan);
        // model.addAttribute("bulan1", (String) pendaftarPerBulan.get(0).get("nama bulan"));
        // model.addAttribute("jumlah1", (Long) pendaftarPerBulan.get(0).get("jumlah"));

        //Punya Iky
        // Ambil semua pelatihan yang dibuat oleh trainer
        List<Pelatihan> pelatihanTakaList = pelatihanService.getTakaPelatihanTrainer();
        model.addAttribute("pelatihanList", pelatihanTakaList);

        List<Pelatihan> pelatihanTabaList = pelatihanService.getTabaPelatihanTrainer();
        model.addAttribute("pelatihanList", pelatihanTabaList);

        List<Pelatihan> pelatihanList = pelatihanService.getAllApprovedPelatihan();
        model.addAttribute("pelatihanList", pelatihanList);


        return "dashboard-trainer";
    }

}
    
    // @GetMapping("/trainer")
    // public String trainerManagerDashboard(Model model) {
    //     // Ambil semua pelatihan yang membutuhkan persetujuan dari trainer manager
    //     List<Pelatihan> pelatihanList = pelatihanService.getAllApprovedPelatihan();
    //     model.addAttribute("pelatihanList", pelatihanList);
    //     return "trainer_manager_dashboard";
    // }


// -------------------------

//         @GetMapping("/trainer")
//     public String trainerDashboard(Model model) {
//         // Ambil semua pelatihan yang dibuat oleh trainer (tipe Gernastastaka)
//         List<Pelatihan> takaPelatihanList = pelatihanService.getTakaPelatihanTrainer();
//         model.addAttribute("takaPelatihanList", takaPelatihanList);
        
//         // Ambil semua pelatihan yang dibuat oleh trainer (tipe Gernastastaba)
//         List<Pelatihan> tabaPelatihanList = pelatihanService.getTabaPelatihanTrainer();
//         model.addAttribute("tabaPelatihanList", tabaPelatihanList);
        
//         // Ambil status dari masing-masing pelatihan
//         for (Pelatihan pelatihan : takaPelatihanList) {
//             // Lakukan pengecekan status dan simpan dalam atribut model
//             String status = getStatusPelatihan(pelatihan);
//             model.addAttribute("status_" + pelatihan.getIdPelatihan(), status);
//         }
        
//         for (Pelatihan pelatihan : tabaPelatihanList) {
//             // Lakukan pengecekan status dan simpan dalam atribut model
//             String status = getStatusPelatihan(pelatihan);
//             model.addAttribute("status_" + pelatihan.getIdPelatihan(), status);
//         }
        
//         return "dashboard-trainer";
//     }

//     private String getStatusPelatihan(Pelatihan pelatihan) {
//         if (pelatihan.getStatusApproval() == 2 || pelatihan.getStatusApproval() == 5) {
//             return "Selesai";
//         } else {
//             return "Berjalan";
//         }
//     }

// }
