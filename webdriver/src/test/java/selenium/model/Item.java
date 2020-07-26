package selenium.model;

public class Item {

    private String name;
    private float price;

    public Item withName(String name) {
        this.name = name;
        return this;
    }

    public Item withPrice(float price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

}
