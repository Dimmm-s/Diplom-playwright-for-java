package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;
    private final Locator title;
    private final Locator cartItems;
    private final Locator checkoutButton;
    private final Locator continueShoppingButton;

    public CartPage(Page page) {
        this.page = page;
        this.title = page.locator("[data-test='title']");
        this.cartItems = page.locator("[data-test='inventory-item']");
        this.checkoutButton = page.locator("[data-test='checkout']");
        this.continueShoppingButton = page.locator("[data-test='continue-shopping']");
    }

    public boolean isOpened() {
        return title.isVisible() && "Your Cart".equals(title.textContent().trim());
    }

    public boolean hasProduct(String productName) {
        return productItem(productName).isVisible();
    }

    public void removeProduct(String productName) {
        productItem(productName).locator("button").click();
    }

    public boolean isEmpty() {
        return cartItems.count() == 0;
    }

    public void checkout() {
        checkoutButton.click();
    }

    public void continueShopping() {
        continueShoppingButton.click();
    }

    private Locator productItem(String productName) {
        return cartItems.filter(new Locator.FilterOptions().setHasText(productName)).first();
    }
}
