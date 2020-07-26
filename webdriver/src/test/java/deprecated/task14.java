package deprecated;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class task14 extends TestBase {

    @Test
    public void test1() {
        // перейти в админ-панель
        loginToAdminSection();

        // перейти в раздел Countries и дождаться загрузки страницы
        By countriesLink = By.cssSelector("#box-apps-menu li a[href*='countries']");

        wait.until(elementToBeClickable(countriesLink));
        driver.findElement(countriesLink).click();

        waitReadyStateToComplete();

        // нажать на первую страну в списке и дождаться загрузки страницы
        By countryLink = By.cssSelector("[name='countries_form'] tr.row td:nth-child(5) a");

        wait.until(elementToBeClickable(countryLink));
        driver.findElement(countryLink).click();

        waitReadyStateToComplete();

        // получить handle текущего окна
        String mainWindowHandle = driver.getWindowHandle();

        // получить список ссылок на страницы, открывающиеся в новом окне
        List<WebElement> externalLinks = driver.findElements(By.xpath("//*[contains(@class, 'fa-external-link')]/.."));
        for (WebElement link : externalLinks) {
            // нажать на ссылку
            link.click();

            // дождаться пока откроется новое окно
            wait.until(numberOfWindowsToBe(2));

            // найти handle нового окна
            Set<String> windowHandles = driver.getWindowHandles();
            for (String handle : windowHandles) {
                if (handle.equals(mainWindowHandle)) continue;

                // переключиться на новое окно
                driver.switchTo().window(handle);

                // закрыть открытое окно
                driver.close();

                // переключиться на главное окно
                driver.switchTo().window(mainWindowHandle);
            }
        }
    }

}