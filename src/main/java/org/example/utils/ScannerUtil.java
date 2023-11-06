package org.example.utils;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner scanner;

    public static Scanner getScanner() {
        if (scanner == null)
            scanner = new Scanner(System.in);
        return scanner;
    }
}
