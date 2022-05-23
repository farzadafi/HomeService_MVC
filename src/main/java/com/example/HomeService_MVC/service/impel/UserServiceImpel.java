package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.repository.UserRepository;
import com.example.HomeService_MVC.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpel implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException(email + " not found!"));
        if(!user.getPassword().equals(password))
            return "ERROR";
        else{
            if(user.getRole().equals(Role.ROLE_ADMIN))
                return "ADMIN";
            else if(user.getRole().equals(Role.ROLE_CUSTOMER))
                return "CUSTOMER";
            else if(user.getRole().equals(Role.ROLE_EXPERT)){
                Expert expert = (Expert) user;
                if(expert.isAccepted())
                    return "EXPERT";
                else
                    return "EXPERT_FALSE";
            }
            else
                return "ERROR";
        }
    }


}
