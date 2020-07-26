package selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.model.Item;
import selenium.pages.CheckoutPage;
import selenium.pages.ItemPage;
import selenium.pages.MainPage;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Application {

    private final WebDriver driver;

    private MainPage mainPage;
    private ItemPage itemPage;
    private CheckoutPage checkoutPage;

    private final List<Item> itemsInCart = new ArrayList<>();

    public Application() {
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("w3c", false);
        options.setExperimentalOption("useAutomationExtension", false);

        driver = new ChromeDriver(options);

        mainPage = new MainPage(driver);
        itemPage = new ItemPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void addItemToCart(int item) {
        mainPage.open();

        // перейти на страницу выбранного продукта
        mainPage.product.get(item).click();
        mainPage.waitReadyStateToComplete();

        String oldQuantity = mainPage.quantity.getText();

        // добавить продукт в корзину
        itemPage.selectAdditionalOption();
        itemPage.addToCart.click();

        // проверить, что продукт добавился
        itemPage.wait.until(not(textToBePresentInElement(mainPage.quantity, oldQuantity)));
    }

    public void removeItemNext() {
        checkoutPage.open();

        int oldItemRowsNumber = checkoutPage.itemRow.size();

        // удалить продукт
        checkoutPage.removeButton.get(0).click();

        // проверить, что продукт удалился
        itemPage.wait.until((driver) -> oldItemRowsNumber != checkoutPage.itemRow.size());
    }

    public List<Item> getCartItems() {
        checkoutPage.open();

        int itemsNumber = checkoutPage.name.size();
        itemsInCart.clear();

        for (int i = 0; i < itemsNumber; i++) {
            checkoutPage.shortcut.get(i).click();

            // задержка на время появления анимации переключения слайдов
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            itemsInCart.add(new Item()
                    .withName(checkoutPage.name.get(i).getText())
                    .withPrice(Float.parseFloat(
                            checkoutPage.price.get(i).getText().replace("$", "")
                    ))
            );
        }

        return itemsInCart;
    }

}
