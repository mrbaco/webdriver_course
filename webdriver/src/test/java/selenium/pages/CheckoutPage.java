package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends Page {

    @FindBy(css = "#box-checkout-cart .item [name='remove_cart_item']")
    public List<WebElement> removeButton;

    @FindBy(css = ".shortcuts .shortcut a")
    public List<WebElement> shortcut;

    @FindBy(css = ".item strong")
    public List<WebElement> name;

    @FindBy(xpath = "//li[@class='item']//p[contains(text(), '$')]")
    public List<WebElement> price;

    @FindBy(css = "#box-checkout-summary tr:not(.header)")
    public List<WebElement> itemRow;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CheckoutPage open() {
        driver.get(this.baseUrl + "checkout");
        waitReadyStateToComplete();

        return this;
    }

}
