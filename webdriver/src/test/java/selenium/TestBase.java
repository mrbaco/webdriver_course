package selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestBase {

    WebDriver driver;
    WebDriverWait wait;

    private final String browser = "chrome";

    long pageLoadTimeout = 10;
    long implicitlyWait = 10;

    @Before
    public void start() {
        switch (browser) {
            case ("chrome"):
                ChromeOptions options = new ChromeOptions();

                options.setExperimentalOption("w3c", false);
                options.setExperimentalOption("useAutomationExtension", false);

                driver = new ChromeDriver(options);

                break;
            case ("firefox"):
                driver = new FirefoxDriver();

                break;
            case ("edge"):
                driver = new EdgeDriver();

                break;
        }

        wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    }

    public void waitReadyStateToComplete() {
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void loginToAdminSection() {
        driver.get("http://localhost/admin/");

        // ожидание загрузки страницы
        waitReadyStateToComplete();

        // вход в админ-панель
        driver.findElement(By.cssSelector("input[name='username']"))
                .sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']"))
                .sendKeys("admin");

        driver.findElement(By.cssSelector("button[name='login']"))
                .click();

        // ожидание загрузки страницы
        waitReadyStateToComplete();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}