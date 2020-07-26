package deprecated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class task9 {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String url1 = "http://localhost/admin/?app=countries&doc=countries";
    private final String url2 = "http://localhost/admin/?app=geo_zones&doc=geo_zones";

    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

    private void login() {
        driver.get("http://localhost/admin/");

        // вход в админ-панель
        driver.findElement(By.cssSelector("input[name='username']"))
                .sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']"))
                .sendKeys("admin");

        driver.findElement(By.cssSelector("button[name='login']"))
                .click();

        // ожидание загрузки страницы
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#box-apps-menu > li:nth-child(1) > a")));
    }

    @Test
    public void test1() {
        // вход в админ-панель и открытие нужного адреса
        login();
        driver.get(url1);

        // выбрать список стран
        List<WebElement> countriesElements = driver.findElements(By.cssSelector("#content table tr:not(.header):not(.footer)"));

        // сохранить список стран в массив и проверить наличие зон
        List<String> countriesList = new ArrayList<>();
        List<String> countriesWithZonesLinksList = new ArrayList<>();

        countriesElements.forEach(countryElement -> {
            String value = countryElement.findElement(By.cssSelector("td:nth-child(5) a")).getText();
            if (!value.equals("")) {
                countriesList.add(value);
                if (Integer.parseInt(countryElement.findElement(By.cssSelector("td:nth-child(6)")).getText()) != 0)
                    countriesWithZonesLinksList.add(countryElement.findElement(By.cssSelector("td:nth-child(5) a")).getAttribute("href"));
            }
        });

        // сортировать список стран по алфавиту
        List<String> countriesListSorted = countriesList;
        countriesListSorted.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        // проверить, что сортировка стран корректна
        Assert.assertEquals(countriesListSorted, countriesList);

        // осуществить переход по зонам
        if (countriesWithZonesLinksList.size() > 0) {
            countriesWithZonesLinksList.forEach(link -> {
                if (!link.equals("")) {
                    driver.get(link);

                    // выбрать список зон
                    List<WebElement> zonesElements = driver.findElements(By.cssSelector("#table-zones tr:not(.header):not(.footer)"));

                    // сохранить список зон в массив
                    List<String> zonesList = new ArrayList<>();

                    zonesElements.forEach(zoneElement -> {
                        if (zoneElement.findElement(By.cssSelector("td")).getAttribute("colspan") == null) {
                            String value = zoneElement.findElement(By.cssSelector("td:nth-child(3)")).getText();
                            if (!value.equals("")) zonesList.add(value);
                        }
                    });

                    // сортировать список зон по алфавиту
                    List<String> zonesListSorted = zonesList;
                    zonesListSorted.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });

                    // проверить, что сортировка зон корректна
                    Assert.assertEquals(zonesListSorted, zonesList);
                }
            });
        }
    }

    @Test
    public void test2() {
        // вход в админ-панель и открытие нужного адреса
        login();
        driver.get(url2);

        // получить количество стран
        int countriesNumber = driver.findElements(By.cssSelector("#content table tr:not(.header):not(.footer)")).size();

        // осуществить переход к каждой стране
        for (int i = 0; i < countriesNumber; i++) {
            driver.findElement(By.cssSelector("#content table tr:not(.header):not(.footer) td:nth-child(3) a")).click();

            // выбрать список зон
            List<WebElement> zonesElements = driver.findElements(By.cssSelector("#table-zones tr:not(.header):not(.footer)"));

            // сохранить список зон в массив
            List<String> zonesList = new ArrayList<>();

            zonesElements.forEach(zoneElement -> {
                if (zoneElement.findElement(By.cssSelector("td")).getAttribute("colspan") == null) {
                    String value = zoneElement.findElement(By.cssSelector("td:nth-child(3) > select option[selected]")).getAttribute("textContent");
                    if (!value.equals("")) zonesList.add(value);
                }
            });

            // сортировать список зон по алфавиту
            List<String> zonesListSorted = zonesList;
            zonesListSorted.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

            // проверить, что сортировка зон корректна
            Assert.assertEquals(zonesListSorted, zonesList);

            // вернуться на исходную страницу
            driver.get(url2);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}