package deprecated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class task11 {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String browser = "firefox";

    private final String userEmail = "email" + System.currentTimeMillis() + "@test.com";

    @Before
    public void start() {
        switch (browser) {
            case ("chrome"):
                driver = new ChromeDriver();
                break;
            case ("firefox"):
                driver = new FirefoxDriver();
                break;
            case ("edge"):
                driver = new EdgeDriver();
                break;
        }

        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void waitUntilPageLoaded() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Test
    public void test1() {
        // открыть главную страницу
        driver.get("http://localhost/");

        // перейти к регистрации
        waitUntilPageLoaded();
        driver.findElement(By.cssSelector("#box-account-login [href*='create']")).click();

        // заполнить поля формы регистрации
        waitUntilPageLoaded();

        driver.findElement(By.cssSelector("[name='firstname']")).sendKeys("Denis");
        driver.findElement(By.cssSelector("[name='lastname']")).sendKeys("Sokolov");
        driver.findElement(By.cssSelector("[name='address1']")).sendKeys("King St. 160, Apt. 2");
        driver.findElement(By.cssSelector("[name='postcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("[name='city']")).sendKeys("Charleston");

        driver.findElement(By.cssSelector("[name='country_code'] ~ span.select2")).click();
        driver.findElement(By.cssSelector("ul[id*='select2-country_code'] li[id*='US']")).click();

        new Select(driver.findElement(By.cssSelector("select[name='zone_code']"))).selectByValue("SC");

        driver.findElement(By.cssSelector("[name='email']")).sendKeys(userEmail);
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("+14815162342");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Password1*");
        driver.findElement(By.cssSelector("[name='confirmed_password']")).sendKeys("Password1*");

        // нажать кнопку регистрации
        driver.findElement(By.cssSelector("[name='create_account']")).click();

        // выйти из аккаунта
        waitUntilPageLoaded();

        driver.findElement(By.cssSelector("#box-account [href*='logout']")).click();

        // заполнить поля формы входа
        waitUntilPageLoaded();

        driver.findElement(By.cssSelector("[name='email']")).sendKeys(userEmail);
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Password1*");

        // нажать кнопку входа
        driver.findElement(By.cssSelector("[name='login']")).click();

        // выйти из аккаунта
        waitUntilPageLoaded();

        driver.findElement(By.cssSelector("#box-account [href*='logout']")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}