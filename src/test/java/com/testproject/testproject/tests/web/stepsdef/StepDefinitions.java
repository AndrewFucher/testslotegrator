package com.testproject.testproject.tests.web.stepsdef;

import com.testproject.testproject.bean.TestConfiguration;
import com.testproject.testproject.service.api.WebPageNavigationService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest
public class StepDefinitions {
    @Autowired
    private WebPageNavigationService webPageNavigationService;
    @Autowired
    private WebDriverManager driverManager;
    @Autowired
    private TestConfiguration testConfiguration;

    private WebDriver driver;
    private WebDriverWait driverWait;

    @Before
    public void setUpDriver() {
        driver = driverManager.create();
        driver.manage().window().maximize();

        driverWait = new WebDriverWait(driver, testConfiguration.getDriverConditionTimoutInSeconds());
    }

    @After
    public void after() {
        driver.quit();
    }

    @Given("starting page")
    public void startingPage() {
        webPageNavigationService.goToLoginPage(driver, driverWait);
    }

    @SneakyThrows
    @When("user signing in with credentials {string} and {string}")
    public void userSigningInWithCredentialsAnd(String username, String password) {
        webPageNavigationService.loginToAdminPanel(driver, driverWait, username, password);
    }

    @Then("user successfully signed in")
    public void userSuccessfullySignedIn() {
        assertThat(driver.getCurrentUrl())
                .contains("/configurator/dashboard");
    }

    @Given("successful authorization with creds {string} and {string}")
    public void startingPage(String username, String password) {
        webPageNavigationService.goToLoginPage(driver, driverWait);
        webPageNavigationService.loginToAdminPanel(driver, driverWait, username, password);
    }

    @When("going to table page with sidebar")
    public void goingToTablePageWithSidebar() {
        webPageNavigationService.goToPlayersTableWithSideBar(driver, driverWait);
    }

    @When("going to table page with link on main page")
    public void goingToTablePageWithLinkOnMainPage() {
        webPageNavigationService.goToPlayersTableWithLinkOnMainPage(driver, driverWait);
    }

    @Then("table with players successfully loaded")
    public void tableWithPlayersSuccessfullyLoaded() {
        assertThat(driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[2]/div[1]/div/a")).isDisplayed())
                .isTrue();
    }

    @Given("on table page with players with creds {string} and {string}")
    public void onTablePageWithPlayersWithCredsAnd(String username, String password) {
        webPageNavigationService.goToLoginPage(driver, driverWait);
        webPageNavigationService.loginToAdminPanel(driver, driverWait, username, password);
        webPageNavigationService.goToPlayersTableWithLinkOnMainPage(driver, driverWait);
    }

    @When("sorting table by column {string}")
    public void sortingTableByColumn(String columnName) {
        webPageNavigationService.sortPlayersTableByColumn(driver, driverWait, columnName);
    }

    @Then("table sorted correctly by column {string}")
    public void tableSortedCorrectly(String columnName) {
        var data = webPageNavigationService.getPlayersTableColumnData(driver, driverWait, columnName);
        assertThat(data)
                .isSorted();
    }
}
