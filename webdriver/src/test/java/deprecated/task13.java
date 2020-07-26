package deprecated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class task13 {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String browser = "chrome";

    long pageLoadTimeout = 10;
    long implicitlyWait = 10;

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

        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    }

    private void waitUntilPageLoad() {
        wait.until(jsReturnsValue("if (document.readyState == 'complete') return true;"));
    }

    private String getCartItemsNumber() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cart .quantity")));
        return driver.findElement(By.cssSelector("#cart .quantity")).getText();
    }

    @Test
    public void test1() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            // открыть главную страницу и дождаться её загрузки
            driver.get("http://localhost/");
            waitUntilPageLoad();

            // открыть первый товар на странице и дождаться загрузки страницы
            driver.findElement(By.cssSelector("li.product")).click();
            waitUntilPageLoad();

            // получить значение товаров в корзине
            String cartItemsNumber = getCartItemsNumber();

            // проверить наличие обязательного поля для заполнения на товаре
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() > 0) {
                new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")))
                        .selectByValue("Small");
            }
            driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);

            // добавить товар в корзину
            driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
            wait.until(not(textToBe(By.cssSelector("#cart .quantity"), cartItemsNumber)));
        }

        // открыть корзину и дождаться загрузки страницы
        driver.findElement(By.cssSelector("#cart [href*='checkout'].link")).click();
        waitUntilPageLoad();

        // небольшая задержка перед началом взаимодействия с корзиной
        Thread.sleep(1000);

        // получить количество позиций на странице
        int itemsNumber = driver.findElements(By.cssSelector("#box-checkout-cart .item")).size();

        for (int i = 0; i < itemsNumber; i++) {
            // удалить элемент из корзины и проверить, что он действительно удалился
            WebElement tableRow = driver.findElement(By.cssSelector("#box-checkout-summary tr:not(.header)"));
            driver.findElement(By.cssSelector("#box-checkout-cart .item [name='remove_cart_item']")).click();
            wait.until(stalenessOf(tableRow));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}