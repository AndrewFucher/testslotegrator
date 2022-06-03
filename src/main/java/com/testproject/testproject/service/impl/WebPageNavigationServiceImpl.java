package com.testproject.testproject.service.impl;

import com.testproject.testproject.bean.HostsConfiguration;
import com.testproject.testproject.service.api.WebPageNavigationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.testproject.testproject.statics.DefaultValues.PLAYERS_TABLE_PAGE_COUNT;

@Service
@RequiredArgsConstructor
public class WebPageNavigationServiceImpl implements WebPageNavigationService {
    private final HostsConfiguration hostsConfiguration;

    @Override
    public void goToLoginPage(WebDriver driver, WebDriverWait driverWait) {
        driver.get(hostsConfiguration.getWebTestsUrl() + "/admin/login");
        driverWait.until(driver1 -> driver1.findElement(By.name("yt0")).isDisplayed());
    }

    @SneakyThrows
    @Override
    public void loginToAdminPanel(WebDriver driver, WebDriverWait driverWait, String username, String password) {
        driver.findElement(By.id("UserLogin_username")).sendKeys(username);
        driver.findElement(By.id("UserLogin_password")).sendKeys(password);
        driver.findElement(By.name("yt0")).click();
        driverWait.until(driver1 -> driver1.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[3]")).isDisplayed());
    }

    @Override
    public void goToPlayersTableWithSideBar(WebDriver driver, WebDriverWait driverWait) {
        driver.findElement(By.xpath("//*[@id=\"nav\"]/li[8]/a")).click();
        driver.findElement(By.cssSelector("#s-menu-users > li:nth-child(1) > a")).click();
        waitUntilPlayersTablePageIsLoaded(driverWait);
    }

    @Override
    public void goToPlayersTableWithLinkOnMainPage(WebDriver driver, WebDriverWait driverWait) {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[3]/a/div/div/p[1]")).click();
        waitUntilPlayersTablePageIsLoaded(driverWait);
    }

    @Override
    public void sortPlayersTableByColumn(WebDriver driver, WebDriverWait driverWait, String columnName) {
        var column = driver.findElement(By.xpath("//a[@class='sort-link' and text()='%s']".formatted(columnName)));
        column.click();
        driverWait.until(ExpectedConditions.attributeContains(
                By.xpath("//a[contains(@class,'sort-link') and text()='%s']".formatted(columnName)), "class", "asc")
        );
    }

    @Override
    public List<String> getPlayersTableColumnData(WebDriver driver, WebDriverWait driverWait, String columnName) {
        var parentOfTextOfColumn = driver.findElement(
                By.xpath("//*[@id=\"payment-system-transaction-grid\"]/table/thead/tr[1]/th[.//a[@class='sort-link asc' and text()='%s']]".formatted(columnName))
        );
        var index = Integer.parseInt(parentOfTextOfColumn.getAttribute("id").split("_c")[1]) + 1;

        var values = new ArrayList<String>();
        for (int i = 1; i <= PLAYERS_TABLE_PAGE_COUNT; i++) {
            var el = driver.findElement(By.xpath("//*[@id=\"payment-system-transaction-grid\"]/table/tbody/tr[%s]/td[%s]".formatted(i, index)));
            values.add(el.getText());
        }

        return values;
    }

    private void waitUntilPlayersTablePageIsLoaded(WebDriverWait driverWait) {
        driverWait.until(driver1 -> ExpectedConditions.elementToBeClickable(driver1.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[2]/div[1]/div/a"))));
    }
}
