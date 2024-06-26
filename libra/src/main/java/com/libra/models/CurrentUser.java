package com.libra.models;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {
    private int id;
    private String name;
    private String username;
    private String email;
    private int roleId;

    private static List<CurrentUser> currentUserList = new ArrayList<>();

    public CurrentUser(int id, String name, String username, String email, int roleId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        currentUserList.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    public static List<CurrentUser> getCurrentUserList() {
        return currentUserList;
    }

    public static void removeFirstUser() {
        if (!currentUserList.isEmpty()) {
            currentUserList.remove(0);
        }
    }
}

