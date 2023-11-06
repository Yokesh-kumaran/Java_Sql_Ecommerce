package org.example.controller;

import org.example.controller.implementation.IHomeController;
import org.example.utils.DbUtil;
import org.example.utils.ExceptionUtil;
import org.example.utils.StringUtils;
import org.example.view.UserPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.InputUtil.enterInt;
import static org.example.utils.PrintUtils.print;
import static org.example.utils.PrintUtils.println;

public class HomeController implements IHomeController {
    private final UserPage userPage;
    private final ProductController productController;
    private final CartController cartController;

    public HomeController() {
        userPage = new UserPage();
        productController = new ProductController();
        cartController = new CartController();
    }
    private String loginUserName;

    @Override
    public void printMenu(String name) {
        loginUserName = name;
        System.out.println();
        System.out.println("******************* WELCOME " + name.toUpperCase() + " *******************");
        userPage.printMenu();
        getChoice();
    }

    @Override
    public void getChoice() {
        try {
            int choice = enterInt(StringUtils.ENTER_CHOICE);
            if (choice == 1) {
                printCategories();
            } else if (choice == 2) {
                productController.printAllProduts();
            } else if (choice == 3) {
                cartController.showcart(loginUserName);
            } else if (choice == 4) {

            } else if (choice == 5) {

            } else {
                println(StringUtils.INVALID_CHOICE);
                getChoice();
            }
        } catch (ExceptionUtil e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printCategories() {
        System.out.println();
        println(StringUtils.CATEGORY);
        Connection connection = DbUtil.getConnection();
        String categoryName;
        if (connection != null) {
            try {
                String query = "SELECT id, name FROM category";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int categoryId = resultSet.getInt("id");
                    categoryName = resultSet.getString("name");
                    System.out.println(categoryId + ". "+categoryName.toUpperCase());
                }
                System.out.println("99. GO BACK");
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
        int choice;
        try {
            choice = enterInt(StringUtils.ENTER_CHOICE);
            if(choice >=1 && choice <= 5){
                productController.viewProducts(choice);
            }else if (choice == 99) {
                System.out.println();
                userPage.printMenu();
                getChoice();
            }else {
                println(StringUtils.INVALID_CHOICE);
                printCategories();
            }
        } catch (ExceptionUtil e) {
            throw new RuntimeException(e);
        }
    }
}
