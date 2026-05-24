package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage {
    private final Page page;
    private final Locator title;
    private final Locator inventoryList;
    private final Locator inventoryItems;
    private final Locator cartBadge;
    private final Locator cartLink;
    private final Locator sortSelect;
    private final Locator menuButton;
    private final Locator logoutLink;
    private final Locator menuWrap;

    public InventoryPage(Page page) {
        this.page = page;
        this.title = page.locator("[data-test='title']");
        this.inventoryList = page.locator("[data-test='inventory-list']");
        this.inventoryItems = page.locator("[data-test='inventory-item']");
        this.cartBadge = page.locator("[data-test='shopping-cart-badge']");
        this.cartLink = page.locator("[data-test='shopping-cart-link']");
        this.sortSelect = page.locator("[data-test='product-sort-container']");
        this.menuButton = page.locator("#react-burger-menu-btn");
        this.logoutLink = page.locator("[data-test='logout-sidebar-link']");
        this.menuWrap = page.locator(".bm-menu-wrap");
    }

    public boolean isOpened() {
        return title.isVisible() && "Products".equals(title.textContent().trim());
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

    public String getCartBadgeText() {
        return cartBadge.textContent().trim();
    }

    public void openCart() {
        cartLink.click();
    }

    public void sortProducts(String value) {
        sortSelect.selectOption(value);
    }

    public String firstProductName() {
        return inventoryItems.first().locator("[data-test='inventory-item-name']").textContent().trim();
    }

    public void openMenu() {
        menuButton.click();
    }

    public void logout() {
        openMenu();
        logoutLink.click();
    }

    public boolean isNavigationMenuAvailable() {
        openMenu();
        return menuWrap.isVisible() && logoutLink.isVisible();
    }

    private Locator productItem(String productName) {
        return inventoryItems.filter(new Locator.FilterOptions().setHasText(productName)).first();
    }
}
