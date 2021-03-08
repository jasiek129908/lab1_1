/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    private Product product;

    private Date productSnapshotDate;

    private int quantity;

    private Money totalCost;

    private Discount discount;

    public OfferItem(String productId, BigDecimal productPrice, String productName, Date productSnapshotDate,
                     String productType, int quantity, BigDecimal discount, String discountCause, String currency) {
        this.product = new Product(productId, productName, productType, new Money(currency, productPrice));
        this.productSnapshotDate = productSnapshotDate;
        this.quantity = quantity;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.subtract(discount);
        }
        this.discount = new Discount(discountCause, discountValue);
        this.totalCost = new Money(currency, productPrice.multiply(new BigDecimal(quantity)).subtract(discountValue));
    }

    public Product getProduct() {
        return product;
    }

    public Date getProductSnapshotDate() {
        return productSnapshotDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public Discount getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferItem offerItem = (OfferItem) o;
        return quantity == offerItem.quantity &&
                Objects.equals(product, offerItem.product) &&
                Objects.equals(productSnapshotDate, offerItem.productSnapshotDate) &&
                Objects.equals(totalCost, offerItem.totalCost) &&
                Objects.equals(discount, offerItem.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, productSnapshotDate, quantity, totalCost, discount);
    }

    /**
     * @param other
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getAmount().compareTo(other.getTotalCost().getAmount()) > 0) {
            max = totalCost.getAmount();
            min = other.totalCost.getAmount();
        } else {
            max = other.totalCost.getAmount();
            min = totalCost.getAmount();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}
