package org.example.controller;

import org.example.controller.implementation.IAuthController;
import org.example.utils.DbUtil;
import org.example.utils.ExceptionUtil;
import org.example.utils.StringUtils;
import org.example.view.AuthPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.InputUtil.enterInt;
import static org.example.utils.InputUtil.enterString;
import static org.example.utils.PrintUtils.println;

public class AuthController implements IAuthController {
    private final AuthPage authPage;
    private final AdminController adminController;
    private HomeController homeController;

    public AuthController() {
        authPage = new AuthPage();
        adminController = new AdminController();
        homeController = new HomeController();
    }

    public void authMenu() {
        authPage.printMenu();
        int choice;
        try {
            choice = enterInt(StringUtils.ENTER_CHOICE);
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else if (choice == 10) {
                System.exit(0);
            } else {
                println(StringUtils.INVALID_CHOICE);
                authMenu();
            }
        } catch (ExceptionUtil e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
        System.out.println();
        println(StringUtils.LOGIN);
        String email = enterString(StringUtils.ENTER_EMAIL);
        String password = enterString(StringUtils.ENTER_PASSWORD);
        Boolean isUserFound = false;
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT name, email, password, roleid FROM user";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int roleId = resultSet.getInt("roleid");
                    String name = resultSet.getString("name");
                    String userName = resultSet.getString("email");
                    String pwd = resultSet.getString("password");
                    if (userName.equals(email) && pwd.equals(password)) {
                        isUserFound = true;
                        if (roleId == 1) {
                            adminController.printMenu(name);
                            break;
                        } else {
                            homeController.printMenu(name);
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                if (isUserFound == false) {
                    authPage.userNotExist();
                    authMenu();
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void register() {
        System.out.println();
        println(StringUtils.REGISTER);
        String username = enterString(StringUtils.ENTER_USERNAME);
        String email = enterString(StringUtils.ENTER_EMAIL);
        String password = enterString(StringUtils.ENTER_PASSWORD);
        String confirmPassword = enterString(StringUtils.ENTER_CONFIRM_PASSWORD);
        int id = 0;

        if (password.equals(confirmPassword)) {
            Connection connection = DbUtil.getConnection();
            if (connection != null) {
                try {
                    String query = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)";
                    String query2 = "SELECT id FROM user ORDER BY id DESC LIMIT 1";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

                    ResultSet resultSet = preparedStatement2.executeQuery();
                    while (resultSet.next()) {
                        id = resultSet.getInt("id") + 1;
                    }

                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, password);
                    preparedStatement.setInt(5, 2);
                    preparedStatement.setBoolean(6, false);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("REGISTRATION SUCCESSFUL..");
                    }
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            println(StringUtils.PASSWORD_MISMATCH);
            register();
        }
        login();
    }
}
