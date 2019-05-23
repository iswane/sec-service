package sn.atos.secservice.service;

import sn.atos.secservice.domaine.AppRole;
import sn.atos.secservice.domaine.AppUser;

public interface AccountService {
    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole saveRole(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username, String roleName);
}
