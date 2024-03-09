package com.webapp.service;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webapp.model.UsersModel;
import com.webapp.repository.UsersRepository;

@Service
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 新規ユーザー登録メソッド
    public UsersModel registerUser(String firstname_hina, String lastname_hina, String firstname_kata, String lastname_kata, String email, String password, Date birthday, String address1, String address2, String address3, String nationality, String sex) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Eメールとパスワードを null にすることはできません");
        }

        // メールがすでに存在するかどうかを確認する
        UsersModel existingUser = usersRepository.findFirstByEmail(email);
        if (existingUser != null) {
            throw new DuplicateEmailException("Eメールもう存在してます");
        }

        UsersModel newUser = new UsersModel();
        newUser.setFirstname_hina(firstname_hina);
        newUser.setLastname_hina(lastname_hina);
        newUser.setFirstname_kata(firstname_kata);
        newUser.setLastname_kata(lastname_kata);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); 
        newUser.setBirthday(birthday);
        newUser.setAddress1(address1);
        newUser.setAddress2(address2);
        newUser.setAddress3(address3);
        newUser.setNationality(nationality);
        newUser.setSex(sex);

        // Save the new user to the repository
        return usersRepository.save(newUser);
    }

    // Method to authenticate a user
    public UsersModel authenticate(String email, String password) {
        UsersModel user = usersRepository.findFirstByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

   
    private static class DuplicateEmailException extends RuntimeException {
        public DuplicateEmailException(String message) {
            super(message);
        }
    }
}