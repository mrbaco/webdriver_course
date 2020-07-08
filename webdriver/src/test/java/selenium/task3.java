package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class task3 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/admin/");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name='login']")));

        driver.findElement(By.cssSelector("input[name='username']"))
                .sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']"))
                .sendKeys("admin");

        driver.findElement(By.cssSelector("button[name='login']"))
                .click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
