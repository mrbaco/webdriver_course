package selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class task17 extends TestBase {

    @Test
    public void test1() {
        // перейти в админ-панель
        loginToAdminSection();

        // перейти в раздел Catalog и дождаться загрузки страницы
        By catalogLink = By.cssSelector("#box-apps-menu li a[href*='catalog']");

        wait.until(elementToBeClickable(catalogLink));
        driver.findElement(catalogLink).click();

        waitReadyStateToComplete();

        // перейти в категорию Rubber Ducks и дождаться загрузки страницы
        By categoryLink = By.xpath("//a[contains(text(), 'Rubber Ducks')]");

        wait.until(elementToBeClickable(categoryLink));
        driver.findElement(categoryLink).click();

        waitReadyStateToComplete();

        // собрать ссылки на все товары со страницы
        List<String> links = new ArrayList<>();

        List<WebElement> productsLinks = driver.findElements(By.xpath("//a[contains(@href, 'product_id')][not(contains(@title, 'Edit'))]"));
        for (WebElement productLink : productsLinks)
            links.add(productLink.getAttribute("href"));

        // перебрать список ссылок
        links.forEach(link -> {
            // открыть по очереди каждую ссылку и дождаться загрузки страницы
            driver.get(link);
            waitReadyStateToComplete();

            // проанализировать лог на наличие чего-либо
            Assert.assertEquals(0, driver.manage().logs().get("browser").getAll().size());
        });
    }

}