package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage extends BasePage {
    private final Locator cartItems;
    private final Locator checkoutButton;
    private final Locator continueShoppingButton;

    public CartPage(Page page) {
        super(page);
        this.cartItems = page.locator("[data-test='inventory-item']");
        this.checkoutButton = page.locator("[data-test='checkout']");
        this.continueShoppingButton = page.locator("[data-test='continue-shopping']");
    }

    public boolean isOpened() {
        return hasHeader() && "Your Cart".equals(getTitle());
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

    public int getItemCount() {
        return cartItems.count();
    }

    public CheckoutPage checkout() {
        checkoutButton.click();
        return new CheckoutPage(page);
    }

    public InventoryPage continueShopping() {
        continueShoppingButton.click();
        return new InventoryPage(page);
    }

    private Locator productItem(String productName) {
        return cartItems.filter(new Locator.FilterOptions().setHasText(productName)).first();
    }
}
