package selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task10 {

    private WebDriver driver;
    private WebDriverWait wait;

    private final HashMap<String, HashMap<String, String>> items = new HashMap<>();
    Pattern colorPattern = Pattern.compile("rgb(a?)\\((\\d{1,3}), (\\d{1,3}), (\\d{1,3})(, (\\d{1,3}))?\\)");

    private final String browser = "firefox";

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

    @Test
    public void test1() {
        // открыть главную страницу
        driver.get("http://localhost/");

        // РАБОТА В КАТАЛОГЕ
        // найти первый элемент в блоке Campaigns
        WebElement campaignItem = driver.findElement(By.cssSelector("#box-campaigns li.product"));

        // создать пустой массив с информацией о продукте
        items.put("catalogPage", new HashMap<>());
        items.put("productPage", new HashMap<>());

        // получить значения названия, обычной и акционной цен в каталоге
        WebElement itemNameInCatalogPage = campaignItem.findElement(By.cssSelector(".name"));
        WebElement itemRegularPriceInCatalogPage = campaignItem.findElement(By.cssSelector(".regular-price"));
        WebElement itemCampaignPriceInCatalogPage = campaignItem.findElement(By.cssSelector(".campaign-price"));

        items.get("catalogPage").put("name", itemNameInCatalogPage.getText());
        items.get("catalogPage").put("regularPrice", itemRegularPriceInCatalogPage.getText());
        items.get("catalogPage").put("campaignPrice", itemCampaignPriceInCatalogPage.getText());

        // проверить, что обычная цена зачеркнутая и серая
        Assert.assertEquals("Обычная цена не зачеркнута!", "S",
                itemRegularPriceInCatalogPage.getAttribute("tagName"));

        Matcher result1 = colorPattern.matcher(itemRegularPriceInCatalogPage.getCssValue("color"));

        Assert.assertTrue("Цвет обычной цены не задан!" + itemRegularPriceInCatalogPage.getCssValue("color"), result1.find());
        Assert.assertTrue("Цвет обычной цены не серый!",
                result1.group(2).equals(result1.group(3)) && result1.group(3).equals(result1.group(4)));

        // проверить, что акционная цена жирная и красная
        Assert.assertEquals("Акционная цена не жирная!", "STRONG",
                itemCampaignPriceInCatalogPage.getAttribute("tagName"));

        Matcher result2 = colorPattern.matcher(itemCampaignPriceInCatalogPage.getCssValue("color"));

        Assert.assertTrue("Цвет акционной цены не задан!", result2.find());
        Assert.assertTrue("Цвет акционной цены не красный!",
                !result2.group(2).equals("0") && result2.group(3).equals("0") && result2.group(4).equals("0"));

        // проверить, что акционная цена крупнее обычной
        Assert.assertTrue("Обычная цена крупнее акционной!",
                Float.parseFloat(itemCampaignPriceInCatalogPage.getCssValue("font-size").replace("px", "")) >
                        Float.parseFloat(itemRegularPriceInCatalogPage.getCssValue("font-size").replace("px", ""))
        );

        // РАБОТА НА СТРАНИЦЕ ЭЛЕМЕНТА
        // нажать на первый элемент в блоке Campaigns
        campaignItem.findElement(By.cssSelector("a")).click();

        // получить значения названия, обычной и акционной цен в каталоге
        WebElement itemNameInProductPage = driver.findElement(By.cssSelector("h1.title"));
        WebElement itemRegularPriceInProductPage = driver.findElement(By.cssSelector(".regular-price"));
        WebElement itemCampaignPriceInProductPage = driver.findElement(By.cssSelector(".campaign-price"));

        items.get("productPage").put("name", itemNameInProductPage.getText());
        items.get("productPage").put("regularPrice", itemRegularPriceInProductPage.getText());
        items.get("productPage").put("campaignPrice", itemCampaignPriceInProductPage.getText());

        // проверить, что обычная цена зачеркнутая и серая
        Assert.assertEquals("Обычная цена не зачеркнута!", "S",
                itemRegularPriceInProductPage.getAttribute("tagName"));

        Matcher result3 = colorPattern.matcher(itemRegularPriceInProductPage.getCssValue("color"));

        Assert.assertTrue("Цвет обычной цены не задан!", result3.find());
        Assert.assertTrue("Цвет обычной цены не серый!",
                result3.group(2).equals(result3.group(3)) && result3.group(3).equals(result3.group(4)));

        // проверить, что акционная цена жирная и красная
        Assert.assertEquals("Акционная цена не жирная!", "STRONG",
                itemCampaignPriceInProductPage.getAttribute("tagName"));

        Matcher result4 = colorPattern.matcher(itemCampaignPriceInProductPage.getCssValue("color"));

        Assert.assertTrue("Цвет акционной цены не задан!", result4.find());
        Assert.assertTrue("Цвет акционной цены не красный!",
                !result4.group(2).equals("0") && result4.group(3).equals("0") && result4.group(4).equals("0"));

        // проверить, что акционная цена крупнее обычной
        Assert.assertTrue("Обычная цена крупнее акционной!",
                Float.parseFloat(itemCampaignPriceInProductPage.getCssValue("font-size").replace("px", "")) >
                        Float.parseFloat(itemRegularPriceInProductPage.getCssValue("font-size").replace("px", ""))
        );

        // проверить, что название, обычная и акционная цены совпадают для страницы каталога и страницы продукта
        Assert.assertEquals("Названия не совпадают!",
                items.get("catalogPage").get("name"), items.get("productPage").get("name"));

        Assert.assertEquals("Обычные цены не совпадают!",
                items.get("catalogPage").get("regularPrice"), items.get("productPage").get("regularPrice"));

        Assert.assertEquals("Акционные цены не совпадают!",
                items.get("catalogPage").get("campaignPrice"), items.get("productPage").get("campaignPrice"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}