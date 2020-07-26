package deprecated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class task7 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        driver.get("http://localhost/admin/");

        // вход в админ-панель
        driver.findElement(By.cssSelector("input[name='username']"))
                .sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']"))
                .sendKeys("admin");

        driver.findElement(By.cssSelector("button[name='login']"))
                .click();

        // ожидание загрузки страницы
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul#box-apps-menu > li:nth-child(1) > a")));

        // подсчет количества ссылок основного меню на странице
        int mainMenuLinkCounter = driver.findElements(By.cssSelector("ul#box-apps-menu > li")).size();

        // поочередное нажатие на каждый элемент
        for (int mainMenuLinkIterator = 0; mainMenuLinkIterator < mainMenuLinkCounter; mainMenuLinkIterator++) {
            driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + (mainMenuLinkIterator + 1) + ") > a")).click();

            // ожидание появления атрибута выбора у нажатого элемента
            wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + (mainMenuLinkIterator + 1) + ")")), "class", "selected"));

            // подсчет количества ссылок подменю в группе
            int subMenuLinkCounter = driver.findElements(By.cssSelector("ul#box-apps-menu > li:nth-child(" + (mainMenuLinkIterator + 1) + ") ul.docs > li")).size();

            if (subMenuLinkCounter > 0) {
                // поочередное нажатие на каждый элемент
                for (int subMenuLinkIterator = 0; subMenuLinkIterator < subMenuLinkCounter; subMenuLinkIterator++) {
                    driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + (mainMenuLinkIterator + 1) + ") ul.docs > li:nth-child(" + (subMenuLinkIterator + 1) + ") > a")).click();

                    // ожидание появления атрибута выбора у нажатого элемента
                    wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + (mainMenuLinkIterator + 1) + ") ul.docs > li:nth-child(" + (subMenuLinkIterator + 1) + ")")), "class", "selected"));

                    // проверка наличия заголовка на странице
                    Assert.assertEquals(1, driver.findElements(By.cssSelector("h1")).size());
                }
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
