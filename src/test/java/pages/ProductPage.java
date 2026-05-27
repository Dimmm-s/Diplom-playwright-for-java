package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage extends BasePage {
    private final Locator productName;
    private final Locator productDescription;
    private final Locator productPrice;
    private final Locator addButton;
    private final Locator removeButton;
    private final Locator backToProductsButton;

    public ProductPage(Page page) {
        super(page);
        this.productName = page.locator("[data-test='inventory-item-name']");
        this.productDescription = page.locator("[data-test='inventory-item-desc']");
        this.productPrice = page.locator("[data-test='inventory-item-price']");
        this.addButton = page.locator("[data-test='add-to-cart']");
        this.removeButton = page.locator("[data-test='remove']");
        this.backToProductsButton = page.locator("[data-test='back-to-products']");
    }

    public boolean isOpenedFor(String expectedProductName) {
        return hasHeader()
                && productName.isVisible()
                && expectedProductName.equals(getProductName());
    }

    public String getProductName() {
        return productName.textContent().trim();
    }

    public String getProductDescription() {
        return productDescription.textContent().trim();
    }

    public String getProductPrice() {
        return productPrice.textContent().trim();
    }

    public void addProductToCart() {
        addButton.click();
    }

    public void removeProductFromCart() {
        removeButton.click();
    }

    public InventoryPage backToProducts() {
        backToProductsButton.click();
        return new InventoryPage(page);
    }
}
