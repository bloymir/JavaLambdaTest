package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainLambda {
    private static final String URL = "https://www.saucedemo.com/";

    public static void main(String[] args) {
        final var start = System.currentTimeMillis();
        final var exmple = new MainLambda();

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

    private MainLambda() {
        WebDriverManager.edgedriver().setup();
        this.driver = new EdgeDriver();
        setTimeouts(t -> t.implicitlyWait(Duration.ofSeconds(10)));
        setTimeouts(t -> t.pageLoadTimeout(Duration.ofSeconds(30)));
        setTimeouts(t -> t.scriptTimeout(Duration.ofSeconds(30)));
        this.driver.get(URL);
    }

    public void login(final String userNameValue, final String passwordValue){
        performAction(this.userNameInput, e -> e.sendKeys(userNameValue));
        performAction(this.passInput, e -> e.sendKeys(passwordValue));
        performAction(this.loginBtn, WebElement::click);;
        final var titlePage = getElementAttribute(this.titlePage, WebElement::getText);
        System.out.println(titlePage);
    }

    public void logout(){
        performAction(this.menuBtn, e -> e.click());
        performAction(this.logoutBtn, e -> e.click());

    }

    private void performAction(final By locator, final Consumer<WebElement> action){
        action.accept(this.driver.findElement(locator));
    }

    private void setTimeouts(final Consumer<WebDriver.Timeouts> timeouts){
        timeouts.accept(this.driver.manage()
                .timeouts());
    }

    private <T> T getElementAttribute(final By locator, final Function<WebElement, T> func){
        return func.apply(this.driver.findElement(locator));
    }

}