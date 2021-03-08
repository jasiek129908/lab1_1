package pl.com.bottega.ecommerce.sales.domain.offer;


import java.util.Objects;

public class Product {

    private String productId;

    private String productName;

    private String productType;

    private Money money;

    public Product(String productId, String productName, String productType, Money money) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.money = money;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public Money getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(productType, product.productType) &&
                Objects.equals(money, product.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productType, money);
    }
}
