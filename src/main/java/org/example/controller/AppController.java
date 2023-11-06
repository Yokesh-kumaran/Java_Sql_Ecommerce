package org.example.controller;

import org.example.controller.implementation.IAppController;
import org.example.utils.DbUtil;
import org.example.view.WelcomePage;

public class AppController implements IAppController {

    private final WelcomePage welcomePage;
    private final AuthController authController;

    public AppController() {
        welcomePage = new WelcomePage();
        authController = new AuthController();
    }

    @Override
    public void init() {
        if(DbUtil.getConnection() != null){
            welcomePage.printMenu();
            authController.authMenu();
        }
    }
}
