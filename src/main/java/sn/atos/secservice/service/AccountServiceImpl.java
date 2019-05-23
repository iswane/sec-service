package sn.atos.secservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sn.atos.secservice.dao.AppRoleRepository;
import sn.atos.secservice.dao.AppUserRepository;
import sn.atos.secservice.domaine.AppRole;
import sn.atos.secservice.domaine.AppUser;

public class AccountServiceImpl implements AccountService {

    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bcpe;

    public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, BCryptPasswordEncoder bcpe) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.bcpe = bcpe;
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("User already exists !");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(bcpe.encode(password));
        appUser.setActive(true);
        appUserRepository.save(appUser);
        addRoleToUser(username, "USER");
        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        appRoleRepository.save(role);
        return role;
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        return user;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role = appRoleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
    }
}
