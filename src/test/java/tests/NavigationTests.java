package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("navigation")
@Tag("positive")
public class NavigationTests extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void login() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
    }

    @Test
    @DisplayName("Користувач переходить на сторінку кошика")
    void userCanOpenCartPage() {
        inventoryPage.openCart();

        assertTrue(new CartPage(page).isOpened());
    }

    @Test
    @DisplayName("Користувач повертається зі сторінки кошика до каталогу товарів")
    void userCanReturnFromCartToProducts() {
        inventoryPage.openCart();
        new CartPage(page).continueShopping();

        assertTrue(inventoryPage.isOpened());
    }

    @Test
    @DisplayName("Користувач відкриває меню")
    void userCanOpenMenu() {
        assertTrue(inventoryPage.menu().hasMainActions());
    }

    @Test
    @DisplayName("Користувач переходить до каталогу з навігаційного меню")
    void userCanOpenCatalogFromMenu() {
        CartPage cartPage = inventoryPage.openCart();

        InventoryPage catalogPage = cartPage.menu().openCatalog();

        assertTrue(catalogPage.isOpened());
    }

    @Test
    @DisplayName("Користувач відкриває сторінку About з навігаційного меню")
    void userCanOpenAboutFromMenu() {
        assertEquals("https://saucelabs.com/", inventoryPage.menu().getAboutUrl());
    }

    @Test
    @DisplayName("Користувач скидає стан застосунку через меню")
    void userCanResetAppStateFromMenu() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        assertEquals(1, inventoryPage.getCartBadgeCount());

        inventoryPage.menu().resetAppState();

        assertEquals(0, inventoryPage.getCartBadgeCount());
    }

    @Test
    @DisplayName("Користувач виконує logout")
    void userCanLogout() {
        inventoryPage.menu().logout();

        assertTrue(new LoginPage(page).isOpened());
    }

    @Test
    @DisplayName("Після logout користувач повертається на сторінку авторизації")
    void loginPageIsDisplayedAfterLogout() {
        LoginPage loginPage = new LoginPage(page);

        inventoryPage.menu().logout();

        assertTrue(loginPage.isOpened());
    }
}
