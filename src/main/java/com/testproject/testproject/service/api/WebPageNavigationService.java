package com.testproject.testproject.service.api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public interface WebPageNavigationService {
    void goToLoginPage(WebDriver driver, WebDriverWait driverWait);

    void loginToAdminPanel(WebDriver driver, WebDriverWait driverWait, String username, String password);

    void goToPlayersTableWithSideBar(WebDriver driver, WebDriverWait driverWait);

    void goToPlayersTableWithLinkOnMainPage(WebDriver driver, WebDriverWait driverWait);

    void sortPlayersTableByColumn(WebDriver driver, WebDriverWait driverWait, String column);

    List<String> getPlayersTableColumnData(WebDriver driver, WebDriverWait driverWait, String column);
}
