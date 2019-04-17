package ru.geekbrains.pocket.backend.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Role {

    ROLE_ADMIN, ROLE_USER;

    public static List<Role> getRoleUser(){
        final List<Role> collection = new ArrayList<Role>();
        //collection.add(Role.ROLE_ADMIN);
        collection.add(Role.ROLE_USER);
        return collection;
    }

    public static List<Privilege> getPrivileges(){
        final List<Privilege> collection = new ArrayList<Privilege>();
        collection.add(Privilege.READ_PRIVILEGE);
        collection.add(Privilege.WRITE_PRIVILEGE);
        collection.add(Privilege.CHANGE_PASSWORD_PRIVILEGE);
        return collection;
    }
}
