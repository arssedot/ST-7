package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "../.github/workflows/web-driver/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        try {
            runTask1(webDriver);
            runTask2(webDriver);
            runTask3(webDriver);
        } finally {
            webDriver.quit();
        }
    }

    private static void runTask1(WebDriver webDriver) {
        System.out.println("№1: Генератор паролей");
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
            WebElement passElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#resultid .verybigtext b")));
            String password = passElement.getText().trim();
            System.out.println("Сгенерированный пароль: " + password);
        } catch (Exception e) {
            System.out.println("Task1 Error: " + e);
        }
    }

    private static void runTask2(WebDriver webDriver) {
        System.out.println("\n№2: IP-адрес");
        try {
            Task2.run(webDriver);
        } catch (Exception e) {
            System.out.println("Task2 Error: " + e);
        }
    }

    private static void runTask3(WebDriver webDriver) {
        System.out.println("\n№3: Прогноз погоды");
        try {
            Task3.run(webDriver);
        } catch (Exception e) {
            System.out.println("Task3 Error: " + e);
        }
    }
}
