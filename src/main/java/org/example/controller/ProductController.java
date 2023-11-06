package org.example.controller;

import org.example.controller.implementation.IProductController;
import org.example.utils.DbUtil;
import org.example.utils.ExceptionUtil;
import org.example.utils.StringUtils;
import org.example.view.ProductPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.InputUtil.enterInt;
import static org.example.utils.PrintUtils.println;

public class ProductController implements IProductController {
    private final ProductPage productPage;
    private final CartController cartController;
    public ProductController() {
        productPage = new ProductPage();
        cartController = new CartController();
    }

    @Override
    public void viewProducts(int id) {
        System.out.println();
        System.out.println("PRODUCTS");
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT id, name, price FROM product WHERE categoryid = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int productId = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    Double productPrice = resultSet.getDouble("price");
                    System.out.println(productId + ". " + productName.toUpperCase() + " - " + productPrice);
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
            int choice;
            try {
                choice = enterInt(StringUtils.ENTER_CHOICE);
                if(choice == 99){
                }
            } catch (ExceptionUtil e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void printAllProduts() {
        productPage.printProducts();
        int choice;
        try {
            choice = enterInt(StringUtils.ENTER_CHOICE);
            if(choice >=1 && choice <= 25){
                cartController.addToCart(choice);
            }else if (choice == 99) {
                System.out.println();
            }else {
                println(StringUtils.INVALID_CHOICE);
                printAllProduts();
            }
        } catch (ExceptionUtil e) {
            throw new RuntimeException(e);
        }
    }
}
