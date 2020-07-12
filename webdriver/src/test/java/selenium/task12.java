package selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class task12 {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String browser = "chrome";
    private final String fileToUpload = "images/duck.jpg";
    private final long randomName = System.currentTimeMillis();

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

    private void login() {
        driver.get("http://localhost/admin/");

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // вход в админ-панель
        driver.findElement(By.cssSelector("input[name='username']"))
                .sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']"))
                .sendKeys("admin");

        driver.findElement(By.cssSelector("button[name='login']"))
                .click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();
    }

    @Test
    public void test1() {
        // вход в админ-панель
        login();

        // переход к разделу Catalog
        driver.findElement(By.cssSelector("#box-apps-menu li a[href*='catalog']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // переход к добавлению нового продукта
        driver.findElement(By.cssSelector("#content a.button[href*='edit_product']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // заполнение формы на вкладке General
        driver.findElement(By.cssSelector("input[name='status'][value='1']")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("Extra rubber duck " + randomName);
        driver.findElement(By.cssSelector("input[name='code']")).sendKeys("extra_rubber_duck");
        driver.findElement(By.cssSelector("input[name='categories[]'][value='0']")).click();
        driver.findElement(By.cssSelector("input[name='categories[]'][value='1']")).click();
        driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("10");
        driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(
                new File("src/test/resources/" + fileToUpload).getAbsolutePath()
        );

        // переход на вкладку Information
        driver.findElement(By.cssSelector("a[href*='#tab-information']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // заполнение формы на вкладке Information
        new Select(driver.findElement(By.cssSelector("[name='manufacturer_id']"))).selectByValue("1");

        driver.findElement(By.cssSelector("[name='keywords']")).sendKeys("extra, rubber, duck");
        driver.findElement(By.cssSelector("[name='short_description[en]']")).sendKeys("another type of duck (test description)");
        driver.findElement(By.cssSelector("[name='head_title[en]']")).sendKeys("Extra rubber duck (sign for title)");

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.height='100px'; arguments[0].style.opacity='1'",
                driver.findElement(By.cssSelector("[name='description[en]']")));

        driver.findElement(By.cssSelector("[name='description[en]']")).sendKeys("<b>I am bold description</b>");

        // переход на вкладку Prices
        driver.findElement(By.cssSelector("a[href*='#tab-prices']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // заполнение формы на вкладке Prices
        driver.findElement(By.cssSelector("[name='purchase_price']")).clear();

        driver.findElement(By.cssSelector("[name='purchase_price']")).sendKeys("12");
        driver.findElement(By.cssSelector("[name='prices[USD]']")).sendKeys("25");

        // сохранение формы
        driver.findElement(By.cssSelector("button[name='save']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // переход к разделу Catalog
        driver.findElement(By.cssSelector("#box-apps-menu li a[href*='catalog']")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // переход к категории, которая содержит новый товар
        driver.findElement(By.xpath("//*[@id='content']//a[contains(text(), 'Rubber Ducks')]")).click();

        // ожидание загрузки страницы
        waitUntilPageLoaded();

        // проверка наличия добавленного элемента
        Assert.assertTrue("Товар не найден в каталоге!",
                driver.findElements(By.xpath("//*[@id='content']//a[contains(text(), '" + randomName + "')]")).size() > 0);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}