package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("inventory")
@Tag("positive")
public class InventoryTests extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void login() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
    }

    @Test
    @DisplayName("Сторінка Products відкривається після авторизації")
    void productsPageIsOpenedAfterLogin() {
        assertTrue(inventoryPage.isOpened());
    }

    @Test
    @DisplayName("На сторінці Products відображається список товарів")
    void productListIsDisplayed() {
        assertTrue(inventoryPage.hasProductList());
    }

    @Test
    @DisplayName("Кількість товарів на сторінці Products відповідає каталогу")
    void productCountIsCorrect() {
        assertEquals(6, inventoryPage.getProductCount());
    }

    @Test
    @DisplayName("Користувач додає товар до кошика")
    void userCanAddProductToCart() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        assertEquals("1", inventoryPage.getCartBadgeText());
    }

    @Test
    @DisplayName("Лічильник кошика змінюється після додавання товару")
    void cartBadgeChangesAfterAddingProduct() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");

        assertEquals("2", inventoryPage.getCartBadgeText());
    }

    @Test
    @DisplayName("Користувач сортує товари на сторінці Products")
    void userCanSortProducts() {
        String firstProductBeforeSorting = inventoryPage.firstProductName();

        inventoryPage.sortProducts("za");

        assertNotEquals(firstProductBeforeSorting, inventoryPage.firstProductName());
    }

    @Test
    @DisplayName("Користувач відкриває навігаційне меню")
    void userCanOpenNavigationMenu() {
        assertTrue(inventoryPage.isNavigationMenuAvailable());
    }

    @Test
    @DisplayName("Користувач виконує logout через меню")
    void userCanLogoutFromMenu() {
        LoginPage loginPage = new LoginPage(page);

        inventoryPage.logout();

        assertTrue(loginPage.isOpened());
    }
}
