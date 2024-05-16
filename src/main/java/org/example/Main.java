package org.example;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class Main {
    private static final String URL = "https://www.saucedemo.com/";

    public static void main(String[] args) {
        final var start = System.currentTimeMillis();
        final var exmple = new  Main();

        try {
            exmple.login("standard_user", "secret_sauce");
            exmple.logout();
        }finally {
            exmple.driver.quit();
            final var end = System.currentTimeMillis();
            System.out.println("Total time in ms: " + (end - start));
        }

    }

    private final WebDriver driver;
    //Locators
    private final By loginBtn = By.id("login-button");
    private final By passInput = By.id("password");
    private final By userNameInput = By.id("user-name");
    private final By titlePage = By.className("title");

    private final By menuBtn = By.id("react-burger-menu-btn");
    private final By logoutBtn = By.id("logout_sidebar_link");

    private Main() {
        WebDriverManager.edgedriver().setup();
        this.driver = new EdgeDriver();
        this.driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
        this.driver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));
        this.driver.manage()
                .timeouts()
                .scriptTimeout(Duration.ofSeconds(30));
        this.driver.get(URL);
    }

    public void login(final String userNameValue, final String passwordValue){
        this.driver.findElement(this.userNameInput)
                .sendKeys(userNameValue);
        this.driver.findElement(this.passInput)
                .sendKeys(passwordValue);
        this.driver.findElement(this.loginBtn)
                .click();

        final var titlePage = this.driver.findElement(this.titlePage)
                .getText();
        System.out.println(titlePage);
    }

    public void logout(){
        this.driver.findElement(this.menuBtn)
                .click();
        this.driver.findElement(this.logoutBtn)
                .click();

    }

}