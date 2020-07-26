package selenium.tests;

import org.junit.Assert;
import org.junit.Test;

public class CartTest extends TestBase {

    @Test
    public void test1() {
        // добавить в корзину 3 продукта
        for (int i = 0; i < 3; i++) {
            app.addItemToCart(i);
        }

        int itemsNumber = app.getCartItems().size();

        // удалить из корзины столько продуктов, сколько было добавлено
        for (int i = 0; i < itemsNumber; i++) {
            app.removeItemNext();
        }

        // проверить, что корзина пуста
        Assert.assertEquals(0, app.getCartItems().size());
    }

}
