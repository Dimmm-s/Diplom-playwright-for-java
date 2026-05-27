package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Menu {
    private final Page page;
    private final Locator menuButton;
    private final Locator closeButton;
    private final Locator menuWrap;
    private final Locator catalogLink;
    private final Locator aboutLink;
    private final Locator logoutLink;
    private final Locator resetAppStateLink;

    public Menu(Page page) {
        this.page = page;
        this.menuButton = page.locator("#react-burger-menu-btn");
        this.closeButton = page.locator("#react-burger-cross-btn");
        this.menuWrap = page.locator(".bm-menu-wrap");
        this.catalogLink = page.locator("[data-test='inventory-sidebar-link']");
        this.aboutLink = page.locator("[data-test='about-sidebar-link']");
        this.logoutLink = page.locator("[data-test='logout-sidebar-link']");
        this.resetAppStateLink = page.locator("[data-test='reset-sidebar-link']");
    }

    public void open() {
        if (!isOpened()) {
            menuButton.click();
        }
    }

    public void close() {
        if (isOpened()) {
            closeButton.click();
        }
    }

    public boolean isOpened() {
        return "false".equals(menuWrap.getAttribute("aria-hidden"));
    }

    public boolean hasMainActions() {
        open();
        return catalogLink.isVisible()
                && aboutLink.isVisible()
                && logoutLink.isVisible()
                && resetAppStateLink.isVisible();
    }

    public InventoryPage openCatalog() {
        open();
        catalogLink.click();
        return new InventoryPage(page);
    }

    public void openAbout() {
        open();
        aboutLink.click();
    }

    public String getAboutUrl() {
        open();
        return aboutLink.getAttribute("href");
    }

    public LoginPage logout() {
        open();
        logoutLink.click();
        return new LoginPage(page);
    }

    public void resetAppState() {
        open();
        resetAppStateLink.click();
    }
}
