package asik.propensik.trainnas.controller;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.TestimoniService;
import asik.propensik.trainnas.service.UserService;
import asik.propensik.trainnas.service.UserServiceImpl;

import java.util.List;

@RequestMapping("/testimoni")
@Controller
public class TestimoniController {

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    PelatihanService pelatihanService;

    @Autowired
    UserServiceImpl userService;

    // @GetMapping("/listTestimoni")
    // public String listTestimoni(Model model) {
    //     List<Testimoni> listTestimoni = testimoniService.getAllTestimoni();
    //     model.addAttribute("listTestimoni", listTestimoni);

    //     return "testimoni/listTestimoni";
    // } 

    @GetMapping("/listTestimoni-admin")
    public String listTestimoniAdmin(Model model) {
        List<Testimoni> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);

        return "testimoni/listTestimoni-admin";
    }

    @GetMapping("/pelatihan/searchTestimoni")
    public String searchTestimoni(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pelatihan> listPelatihan;
        listPelatihan = pelatihanService.searchPelatihanByJudul(searchQuery);
        model.addAttribute("listPelatihan", listPelatihan);
        return "testimoni/listTestimoni-admin";
    }

    // @PostMapping("/add")
    // public String addTestimoni(@RequestParam("komentar") String komentar,
    // @RequestParam("nama") String nama,
    // @RequestParam("tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date
    // tanggal) {

    // Testimoni testimoni = new Testimoni();
    // testimoni.setKomentar(komentar);
    // testimoni.setNama(nama);
    // testimoni.setTanggal(tanggal);

    // testimoniService.addTestimoni(testimoni);
    // return "testimoni/success-add-testimoni";
    // }

    // @PostMapping("/add")
    // public String addTestimoni(@RequestParam("komentar") String komentar,
    // @RequestParam("nama") String nama) {

    // Testimoni testimoni = new Testimoni();
    // testimoni.setKomentar(komentar);

    // // Mendapatkan tanggal dan waktu saat ini
    // Date tanggal = new Date();
    // testimoni.setTanggal(tanggal);

    // testimoniService.addTestimoni(testimoni);
    // return "testimoni/success-add-testimoni";
    // }

    // @GetMapping("/add")
    // public String addTestimoni() {
    // return "testimoni/addTestimoni";
    // }

    @PostMapping("/add/{id}")
    public String addTestimoni(@RequestParam("komentar") String komentar, @PathVariable("id") Long id) {

        System.out.println("masuk1");

        Testimoni testimoni = new Testimoni();
        testimoni.setKomentar(komentar);
        System.out.println("masuk2");

        // Mendapatkan tanggal dan waktu saat ini
        Date tanggal = new Date();
        testimoni.setTanggal(tanggal);
        System.out.println("masuk3");

        Pelatihan pelatihan = pelatihanService.getPelatihanById(id);
        System.out.println("masuk3,5");
        testimoni.setPelatihan(pelatihan);
        System.out.println("masuk4");

        UserModel user = userService.yangSedangLogin();

        // Mengaitkan testimoni dengan pengguna
        testimoni.setUser(user);

        testimoniService.addTestimoni(testimoni);

        System.out.println("masuk5");
        return "testimoni/success-add-testimoni";

    }

    // @GetMapping("/delete")
    // public String deleteTestimoni(@RequestParam("id") Long id) {
    //     testimoniService.deleteTestimoni(id);
    //     return "redirect:/testimoni/listTestimoni-admin";
    // }

    // @PostMapping("/delete")
    // public String deleteTestimoni(@RequestParam("id") Long id) {
    //     testimoniService.deleteTestimoni(id);
    //     return "redirect:/testimoni/listTestimoni-admin";
    // }

    @PostMapping("/delete/{id}")
    public String deleteTestimoni(@PathVariable("id") Long id) {
        testimoniService.deleteTestimoni(id);
        return "redirect:/testimoni/listTestimoni-admin";
    }


    // @GetMapping("/searchTestimoni")
    // public String searchTestimoni(@RequestParam("searchQuery") String search,
    // Model model) {

    // List<Testimoni> listTestimoni;
    // if (search == null || search.isEmpty()) {
    // listTestimoni = testimoniService.getAllTestimoni();
    // } else {
    // listTestimoni = testimoniService.searchTestimoni(search);
    // }

    // model.addAttribute("listTestimoni", listTestimoni);
    // return "testimoni/listTestimoni";
    // }

}
