package org.example.view;

import org.example.utils.ScannerUtil;
import org.example.utils.StringUtils;

import static org.example.utils.PrintUtils.print;
import static org.example.utils.PrintUtils.println;

public class AuthPage {
    public void printMenu() {
        print(StringUtils.AUTH_MENU);
    }

    public void userNotExist() {
        println(StringUtils.USER_NOT_EXIST);
    }
}
