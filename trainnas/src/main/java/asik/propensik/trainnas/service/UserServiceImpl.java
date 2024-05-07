package asik.propensik.trainnas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.model.PelatihanTrainee;
import asik.propensik.trainnas.model.UserModel;
import asik.propensik.trainnas.repository.PelatihanTraineeDb;
import asik.propensik.trainnas.repository.UserDb;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Autowired
    private PelatihanTraineeDb pelatihanTraineeDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<PelatihanTrainee> listPelatihan() {
        return pelatihanTraineeDb.findAll();
    }

    public List<UserModel> getAllUsers() {
        return userDb.findAll();
    }

    // public UserModel getUserById(UUID id) {
    // return userDb.findById(id)
    // .orElseThrow(() -> new IllegalArgumentException("User not found with id " +
    // id));
    // }

    public UserModel getUserByEmail(String email) {
        return userDb.findByEmail(email);
    }

    // public void updateUser(Long id, String role) {
    // var user = getUserById(id);
    // user.setRole(role);
    // userDb.save(user);
    // }

    // public void activatedUser(Long id, boolean activation) {
    // var user = getUserById(id);
    // user.setActive(activation);
    // userDb.save(user);
    // }

    public List<UserModel> searchUserByNama(String searchQuery) {
        // Lakukan pencarian berdasarkan judul dengan metode yang sesuai dari repository
        // Anda
        List<UserModel> searchResults = userDb.findByNameContainingIgnoreCase(searchQuery);
        return searchResults;
    }

    // public List<UserModel> getUserTrainer() {
    // // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
    // return userDb.findByRole("Trainer");
    // }

    // public List<UserModel> getUserTrainee() {
    // // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
    // return userDb.findByRole("Trainee");
    // }

    // public List<UserModel> getUserCommunity() {
    // // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
    // return userDb.findByRole("Community");
    // }

    // public void setPhoneNumber(Long id, String phoneNumber) {
    // var user = getUserById(id);
    // user.setPhoneNumber(phoneNumber);
    // userDb.save(user);
    // }

    // public void setSchool(Long id, String school) {
    // var user = getUserById(id);
    // user.setSchool(school);
    // userDb.save(user);
    // }

}
