package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

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
        assertTrue(inventoryPage.isNavigationMenuAvailable());
    }

    @Test
    @DisplayName("Користувач виконує logout")
    void userCanLogout() {
        inventoryPage.logout();

        assertTrue(new LoginPage(page).isOpened());
    }

    @Test
    @DisplayName("Після logout користувач повертається на сторінку авторизації")
    void loginPageIsDisplayedAfterLogout() {
        LoginPage loginPage = new LoginPage(page);

        inventoryPage.logout();

        assertTrue(loginPage.isOpened());
    }
}
