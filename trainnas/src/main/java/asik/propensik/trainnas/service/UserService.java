package asik.propensik.trainnas.service;

import java.util.List;

import asik.propensik.trainnas.model.PelatihanTrainee;
import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.model.UserModel;

public interface UserService {
    public UserModel addUser(UserModel user);
    public String encrypt(String password);
    public List<PelatihanTrainee> listPelatihan();
    public List<Testimoni> getAllTestimoniByLoggedInUser();
    public UserModel findByUsername(String username);
    public List<UserModel> getAllUser();
    public void deleteUser(String username);
}



// package asik.propensik.trainnas.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import asik.propensik.trainnas.model.UserModel;
// import asik.propensik.trainnas.repository.UserDb;

// @Service
// public class UserService {
//     @Autowired
//     private UserDb userRepository;

//     public void addUser(UserModel user) {
//     userRepository.save(user);
//    }

//     public List<UserModel> getAllUsers() {
//         return userRepository.findAll();
//     }

//     public UserModel getUserById(Long id) {
//         return userRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
//     }

//     public void updateUser(Long id, String role){
//         var user = getUserById(id);
//         user.setRole(role);
//         userRepository.save(user);
//     }

//     public void activatedUser(Long id, boolean activation){
//         var user = getUserById(id);
//         user.setActive(activation);
//         userRepository.save(user);
//     }

//     public List<UserModel> searchUserByNama(String searchQuery) {
//         // Lakukan pencarian berdasarkan judul dengan metode yang sesuai dari repository
//         // Anda
//         List<UserModel> searchResults = userRepository.findByNameContainingIgnoreCase(searchQuery);
//         return searchResults;
//     }

//     public List<UserModel> getUserTrainer() {
//         // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
//         return userRepository.findByRole("Trainer");
//     }

//     public List<UserModel> getUserTrainee() {
//         // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
//         return userRepository.findByRole("Trainee");
//     }

//     public List<UserModel> getUserCommunity() {
//         // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
//         return userRepository.findByRole("Community");
//     }

//     public void setPhoneNumber(Long id, String phoneNumber){
//         var user = getUserById(id);
//         user.setPhoneNumber(phoneNumber);
//         userRepository.save(user);
//     }

//     public void setSchool(Long id, String school){
//         var user = getUserById(id);
//         user.setSchool(school);
//         userRepository.save(user);
//     }

    

//     // tambahkan method lain jika diperlukan
// }
