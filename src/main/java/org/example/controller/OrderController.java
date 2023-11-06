package org.example.controller;

import org.example.controller.implementation.IOrderController;
import org.example.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderController implements IOrderController {
    @Override
    public void placeOrders(String loginUserName) {
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            try {

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
