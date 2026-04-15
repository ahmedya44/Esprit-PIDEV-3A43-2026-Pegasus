package com.pegasus;

import com.pegasus.entities.Admin;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceAdmin;
import com.pegasus.services.ServiceUser;

import java.time.LocalDate;

public class TestServiceFlow {
    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceAdmin serviceAdmin = new ServiceAdmin();

        long suffix = System.currentTimeMillis();
        User user = new User();
        user.setEmail("admin" + suffix + "@pegasus.dev");
        user.setRoles("[\"ROLE_ADMIN\"]");
        user.setPassword("change_me");
        user.setUsername("admin_" + suffix);
        user.setPhone("00000000");
        user.setAvatarUrl(null);
        user.setStatus("ACTIVE");
        user.setDtype("admin");

        serviceUser.ajouter(user);
        if (user.getId() == null) {
            System.err.println("User insert failed, subtype insert skipped.");
            return;
        }

        Admin admin = new Admin();
        admin.setId(user.getId());
        admin.setSuperAdmin(false);
        admin.setBirthDate(LocalDate.of(2000, 1, 1));

        serviceAdmin.ajouter(admin);
        serviceAdmin.getOneById(user.getId());
    }
}
