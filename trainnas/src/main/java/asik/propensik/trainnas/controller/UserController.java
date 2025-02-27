package asik.propensik.trainnas.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asik.propensik.trainnas.model.Role;
// import asik.propensik.trainnas.dto.request.CreateUserRequestDTO;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.service.RoleService;
import asik.propensik.trainnas.service.UserService;



@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/trainee/add")
    public String addTraineeFormPage(Model model){
        UserModel user = new UserModel();
        // List<String> userRole = new ArrayList<>(List.of("admin", "trainee", "trainer","community","trainer manager"));
        model.addAttribute("user", user);
        // model.addAttribute("listRole", userRole);
        return "trainee/form-registrasi-trainee";
    }

    @PostMapping("/trainee/add")
    public String addTraineeFormSubmit(Model model, @ModelAttribute UserModel userModel){
        var roleTrainee = roleService.findByRole("trainee");
        userModel.setRole(roleTrainee);
        userModel.setCreatedAt(LocalDateTime.now());
        userService.addUser(userModel);
        return "home";
    }

    @GetMapping("/user/add")
    public String addUserFormPage(Model model){
        UserModel user = new UserModel();
        List<Role> listUserRole = roleService.findAllRole();
        model.addAttribute("listRole", listUserRole);
        model.addAttribute("user", user);
        return "registerUser";
    }

    @PostMapping("/user/add")
    public String addUserFormSubmit(Model model, @ModelAttribute UserModel userModel){
        userModel.setCreatedAt(LocalDateTime.now());
        userService.addUser(userModel);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profilUser(Model model){
        UserModel user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "profile";
    }

    // @GetMapping("/profile/{username}")
    // public String profilUserbyUsername(Model model, @PathVariable("username") String username){
    //     UserModel user = userService.findByUsername(username);
    //     model.addAttribute("user", user);
    //     return "profile";
    // }

    // @GetMapping("/delete/user/{username}")
    // public String hapusUser(@PathVariable("username") String username) {
    //     userService.deleteUser(username);
    //     return "user-list";
    // }

    @PostMapping("/delete/user/{username}")
    public String hapusUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "user-list";
    }

    @GetMapping("/profile/update/{username}")
    public String profileUpdate(Model model, @PathVariable("username") String username){
        UserModel user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile-update";
    }

    @PostMapping("/profile/update/{username}")
    public String profileUpdate(Model model,
            @PathVariable("username") String username,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("asalSekolah") String asalSekolah){

        UserModel userData = userService.findByUsername(username);
        userData.setName(name);
        userData.setEmail(email);
        userData.setAsalSekolah(asalSekolah);
        userService.addUser(userData);
        return "redirect:/profile";
    }

    @GetMapping("/user/list")
    public String listUser(Model model) {

        List<UserModel> listUser = userService.getAllUser();

        model.addAttribute("listUser", listUser);
        return "user-list";
    }


    @GetMapping("/login")
    public String loginUser() {
        return "login";
    }

    // @GetMapping("barang/{id}")
    // public String detailGudang(@PathVariable("id") String sku, Model model){
    //     Barang barang = barangService.getBarangById(sku);
    //     List<GudangBarang> listTmp = barangService.getBarangById(sku).getListGudangBarang();
    //     int stok = 0;
    //     for(GudangBarang i:listTmp){
    //         stok += i.getStok();
    //     }

    //     model.addAttribute("barang", barang);
    //     model.addAttribute("listGudangBarang", listTmp);
    //     model.addAttribute("stok", stok);

    //     return "view-barang";
    // }



    // @GetMapping("/users")
    // public String getAllUsers(Model model) {
    //     List<UserModel> users = userService.getAllUsers();
    //     model.addAttribute("users", users);
    //     model.addAttribute("tipeRole", "All");
    //     return "user-list"; // Nama file HTML untuk menampilkan daftar user
    // }

   
    
    // @GetMapping("/users/profile-update")
    // public String formUpdateProfile(Model model, @RequestParam("id") Long idUser) {
    //     var user = userService.getUserById(idUser);
    //     model.addAttribute("user", user);
    //     return "profile";
    // }

    // @PostMapping("/users/profile-update")
    // public String formUpdateProfile(Model model,@RequestParam("id") Long id, @RequestParam("school") String school, @RequestParam("phoneNumber") String phoneNumber) {
    //     userService.setPhoneNumber(id, phoneNumber);
    //     userService.setSchool(id, school);        
    //     return "home";
    // }
    
    // @GetMapping("/users/{id}")
    // public String getUserById(@PathVariable Long id, Model model) {
    //     UserModel user = userService.getUserById(id);
    //     model.addAttribute("user", user);
    //     return "user-detail"; // Nama file HTML untuk menampilkan detail user
    // }

    // @GetMapping("/users/detail")
    // public String detailUser(@RequestParam("id") Long idUser, Model model){
    //     var user = userService.getUserById(idUser);
    //     model.addAttribute("user", user);
    //     return "user-detail";
    // }
    
    // @GetMapping("/users/user-update")
    // public String formUpdateUser(Model model, @RequestParam("id") Long idUser) {
    //     var user = userService.getUserById(idUser);
    //     model.addAttribute("user", user);
    //     return "user-update";
    // }

    // @PostMapping("/users/user-update")
    // public String formUpdateUser(@RequestParam("id") Long id, Model model, @RequestParam("role") String role, @RequestParam("isActive") Boolean isActive){
    //     userService.updateUser(id,role);
    //     userService.activatedUser(id, isActive);
    //     return "redirect:/users";

    // }

    // @GetMapping("users/search-user")
    // public String searchUser(@RequestParam("searchQuery") String searchQuery, Model model) {
    //     List<UserModel> listUser;
    //     listUser = userService.searchUserByNama(searchQuery);
    //     model.addAttribute("users", listUser);
    //     return "user-list";
    // }

    // @GetMapping("users/filter-user")
    // public String filterUser(@RequestParam("sortType") String sortType, Model model) {
    //     System.out.println("masuk filter");
    //     if ("All".equals(sortType)) {
    //         List<UserModel> listUser = userService.getAllUsers();
    //         model.addAttribute("users", listUser);
    //         model.addAttribute("tipeRole", "All");
    //     } else if ("Trainer".equals(sortType)) {
    //         List<UserModel> listUser = userService.getUserTrainer();
    //         model.addAttribute("users", listUser);
    //         model.addAttribute("tipeRole", "Trainer");
    //     } else if ("Trainee".equals(sortType)) {
    //         List<UserModel> listUser = userService.getUserTrainee();
    //         model.addAttribute("users", listUser);
    //         model.addAttribute("tipeRole", "Trainee");
    //     } else if ("Community".equals(sortType)) {
    //         List<UserModel> listUser = userService.getUserCommunity();
    //         model.addAttribute("users", listUser);
    //         model.addAttribute("tipeRole", "Community");
    // }
    //     return "user-list";
    // }
}
    
