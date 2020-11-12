package com.example.demo.auth;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ApplicationUserDao {

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);



}
