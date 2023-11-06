package org.example.controller;

import org.example.controller.implementation.IAdminController;
import org.example.view.AdminPage;

public class AdminController implements IAdminController {
    AdminPage adminPage;
    public AdminController() {
        adminPage = new AdminPage();
    }

    @Override
    public void printMenu(String name) {
        System.out.println();
        System.out.println("************************ WELCOME " + name.toUpperCase() + " ************************");
    }
}
