package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends Page {

    @FindBy(css = "li.product")
    public List<WebElement> product;

    @FindBy(css = "#cart .quantity")
    public WebElement quantity;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage open() {
        driver.get(this.baseUrl);
        waitReadyStateToComplete();

        return this;
    }

}
