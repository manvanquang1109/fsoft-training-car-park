package com.example.carpark.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("database")
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    public ApplicationUserService(@Qualifier("database-application-user-dao-impl") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load user by username: " + username);

        return applicationUserDao.findApplicationUserByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("something wrong when loading user by username: " + username);
                    return new UsernameNotFoundException("not found " + username);
                });
    }
}
