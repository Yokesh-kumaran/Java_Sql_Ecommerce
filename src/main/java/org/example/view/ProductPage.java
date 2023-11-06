package org.example.view;

import org.example.utils.DbUtil;
import org.example.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.PrintUtils.println;

public class ProductPage {
    public void printProducts() {
        System.out.println();
        println(StringUtils.PRODUCT);
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT id, name, price FROM product";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int productId = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    Double productPrice = resultSet.getDouble("price");
                    System.out.println(productId + ". "+productName.toUpperCase()+" - "+productPrice);
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
    }
}
