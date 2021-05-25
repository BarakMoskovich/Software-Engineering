package Model.memento;

import Model.Product;
import java.util.Map;

public class Memento {
    private final Map<String, Product> products;

    public Memento(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Product> getProducts() {
        return products;
    }
}