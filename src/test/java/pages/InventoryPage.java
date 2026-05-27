package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class InventoryPage extends BasePage {
    private final Locator inventoryList;
    private final Locator inventoryItems;
    private final Locator sortSelect;

    public InventoryPage(Page page) {
        super(page);
        this.inventoryList = page.locator("[data-test='inventory-list']");
        this.inventoryItems = page.locator("[data-test='inventory-item']");
        this.sortSelect = page.locator("[data-test='product-sort-container']");
    }

    public boolean isOpened() {
        return hasHeader() && "Products".equals(getTitle());
    }

    public boolean hasProductList() {
        return inventoryList.isVisible() && inventoryItems.count() > 0;
    }

    public int getProductCount() {
        return inventoryItems.count();
    }

    public void addProductToCart(String productName) {
        productItem(productName).locator("button").click();
    }

    public void removeProductFromCatalog(String productName) {
        productItem(productName).locator("button").click();
    }

    public ProductPage openProduct(String productName) {
        productItem(productName).locator("[data-test='inventory-item-name']").click();
        return new ProductPage(page);
    }

    public void sortProducts(String value) {
        sortSelect.selectOption(value);
    }

    public String firstProductName() {
        return inventoryItems.first().locator("[data-test='inventory-item-name']").textContent().trim();
    }

    public double firstProductPrice() {
        return parsePrice(inventoryItems.first().locator("[data-test='inventory-item-price']").textContent());
    }

    public List<String> productNames() {
        return inventoryItems.locator("[data-test='inventory-item-name']").allTextContents()
                .stream()
                .map(String::trim)
                .toList();
    }

    public boolean hasProblemPlaceholderImages() {
        Locator images = inventoryItems.locator("img");
        for (int i = 0; i < images.count(); i++) {
            String source = images.nth(i).getAttribute("src");
            if (source != null && source.contains("sl-404")) {
                return true;
            }
        }
        return false;
    }

    private Locator productItem(String productName) {
        return inventoryItems.filter(new Locator.FilterOptions().setHasText(productName)).first();
    }

    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", "").trim());
    }
}
