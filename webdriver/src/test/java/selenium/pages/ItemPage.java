package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class ItemPage extends Page {

    @FindBy(css = "h1.title")
    public WebElement name;

    @FindBy(css = "button[name='add_cart_product']")
    public WebElement addToCart;

    public ItemPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ItemPage selectAdditionalOption() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() > 0) {
            new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")))
                    .selectByValue("Small");
        }

        driver.manage().timeouts().implicitlyWait(implicitlyWaitTimeout, TimeUnit.SECONDS);

        return this;
    }

}
