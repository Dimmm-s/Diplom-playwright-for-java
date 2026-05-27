package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected final Page page;
    private final Locator appLogo;
    private final Locator title;
    private final Locator cartLink;
    private final Locator cartBadge;
    private final Menu menu;

    protected BasePage(Page page) {
        this.page = page;
        this.appLogo = page.locator(".app_logo");
        this.title = page.locator("[data-test='title']");
        this.cartLink = page.locator("[data-test='shopping-cart-link']");
        this.cartBadge = page.locator("[data-test='shopping-cart-badge']");
        this.menu = new Menu(page);
    }

    public Menu menu() {
        return menu;
    }

    public boolean hasHeader() {
        return appLogo.isVisible() && cartLink.isVisible();
    }

    public String getTitle() {
        return title.textContent().trim();
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage(page);
    }

    public String getCartBadgeText() {
        return cartBadge.isVisible() ? cartBadge.textContent().trim() : "0";
    }

    public int getCartBadgeCount() {
        return Integer.parseInt(getCartBadgeText());
    }
}
