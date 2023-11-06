package org.example.controller;

import org.example.controller.implementation.ICartController;
import org.example.utils.DbUtil;
import org.example.utils.ExceptionUtil;
import org.example.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.InputUtil.enterInt;
import static org.example.utils.InputUtil.enterString;
import static org.example.utils.PrintUtils.println;

public class CartController implements ICartController {
    private final OrderController orderController;

    public CartController() {
        orderController = new OrderController();
    }

    @Override
    public void addToCart(int choice) {
        String userName = enterString(StringUtils.ENTER_USERNAME);
        int userId = 0;
        userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT id, userid, productid, count FROM cart";
                String query2 = "SELECT id FROM user WHERE name = ?;";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, userName);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet2.next()) {
                    userId = resultSet2.getInt("id");
                }


                if (!resultSet.next()) {
                    String query3 = "INSERT INTO cart (userid, productid, count) VALUES (?, ?, ?);";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setInt(1, userId);
                    preparedStatement3.setInt(2, choice);
                    preparedStatement3.setInt(3, 1);
                    int rowsInserted = preparedStatement3.executeUpdate();
                    if (rowsInserted > 0) {
                        println("PRODUCT ADDED TO CART...");
                    }
                    preparedStatement3.close();
                } else {
                    int flag = 0;
                    int userid;
                    int productId;
                    int productCount;
                    do {
                        userid = resultSet.getInt("userid");
                        productId = resultSet.getInt("productid");
                        productCount = resultSet.getInt("count");

                        if (userId == userid && productId == choice) {
                            productCount += 1;
                            String query4 = "UPDATE cart SET count = ? WHERE productid = ?;";
                            PreparedStatement preparedStatement4 = connection.prepareStatement(query4);
                            preparedStatement4.setInt(1, productCount);
                            preparedStatement4.setInt(2, productId);
                            int rowsInserted = preparedStatement4.executeUpdate();
                            if (rowsInserted > 0) {
                                println("PRODUCT ADDED TO CART...");
                            }
                            preparedStatement4.close();
                            break;
                        } else if (userId == userid) {
                            if (productId == choice) {
                                flag = 1;
                            }
                        } else {
                            flag = 2;
                        }
                    } while (resultSet.next());
                    if (flag == 0 || flag == 2) {
                        String query5 = "INSERT INTO cart (userid, productid, count) VALUES (?, ?, ?);";
                        PreparedStatement preparedStatement5 = connection.prepareStatement(query5);
                        preparedStatement5.setInt(1, userid);
                        preparedStatement5.setInt(2, choice);
                        preparedStatement5.setInt(3, 1);
                        int rowsInserted = preparedStatement5.executeUpdate();
                        if (rowsInserted > 0) {
                            println("PRODUCT ADDED TO CART...");
                        }
                        preparedStatement5.close();
                    }
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

    @Override
    public void showcart(String loginUserName) {
        loginUserName = loginUserName.substring(0, 1).toUpperCase() + loginUserName.substring(1);
        System.out.println();
        println(StringUtils.CART);
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {
                int userId = 0;
                String query = "SELECT p.name, c.count, p.price FROM cart c INNER JOIN product p ON c.productid = p.id WHERE c.productid = ? AND c.userid = ? AND isordered = false;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                String query2 = "SELECT productid, userid FROM cart";
                String query3 = "SELECT id FROM user WHERE name = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                preparedStatement3.setString(1, loginUserName);
                ResultSet resultSet3 = preparedStatement3.executeQuery();
                if (resultSet3.next()) {
                    userId = resultSet3.getInt("id");
                }
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                int id = 1;
                int total = 0;
                while (resultSet2.next()) {
                    int productId = resultSet2.getInt("productid");
                    preparedStatement.setInt(1, productId);
                    preparedStatement.setInt(2, userId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String productName = resultSet.getString("name");
                        int productCount = resultSet.getInt("count");
                        double productPrice = resultSet.getDouble("price");
                        productPrice = productPrice * productCount;
                        total += productPrice;
                        System.out.println(id + ". " + productName.toUpperCase() + " " + productCount + " - " + productPrice);
                        id += 1;
                    }
                }
                System.out.println("   TOTAL - " + total);
                preparedStatement.close();
                preparedStatement2.close();
                resultSet2.close();
                System.out.println("100. CHECKOUT");
                int choice = enterInt(StringUtils.ENTER_CHOICE);
                if (choice == 100) {
                    orderController.placeOrders(loginUserName);
                } else {
                    println(StringUtils.INVALID_CHOICE);
                    showcart(loginUserName);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ExceptionUtil e) {
                throw new RuntimeException(e);
            }
        }
    }
}
