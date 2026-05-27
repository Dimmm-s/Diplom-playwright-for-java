package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("product")
@Tag("positive")
public class ProductTests extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";
    private InventoryPage inventoryPage;

    @BeforeEach
    void login() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
    }

    @Test
    @DisplayName("Користувач відкриває сторінку товару з каталогу")
    void userCanOpenProductPageFromCatalog() {
        ProductPage productPage = inventoryPage.openProduct(PRODUCT_NAME);

        assertTrue(productPage.isOpenedFor(PRODUCT_NAME));
        assertEquals("$29.99", productPage.getProductPrice());
    }

    @Test
    @DisplayName("Користувач додає і видаляє товар зі сторінки товару")
    void userCanAddAndRemoveProductFromProductPage() {
        ProductPage productPage = inventoryPage.openProduct(PRODUCT_NAME);

        productPage.addProductToCart();
        assertEquals(1, productPage.getCartBadgeCount());

        productPage.removeProductFromCart();
        assertEquals(0, productPage.getCartBadgeCount());

        InventoryPage catalogPage = productPage.backToProducts();
        assertTrue(catalogPage.isOpened());
    }
}
