package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("cart")
@Tag("positive")
public class CartTests extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void login() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
    }

    @Test
    @DisplayName("Користувач додає товар у кошик")
    void userCanAddProductToCart() {
        inventoryPage.addProductToCart(PRODUCT_NAME);

        assertTrue("1".equals(inventoryPage.getCartBadgeText()));
    }

    @Test
    @DisplayName("У кошику відображається доданий товар")
    void addedProductIsDisplayedInCart() {
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();

        assertTrue(cartPage.isOpened());
        assertTrue(cartPage.hasProduct(PRODUCT_NAME));
    }

    @Test
    @DisplayName("Користувач видаляє товар із кошика")
    void userCanRemoveProductFromCart() {
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();

        cartPage.removeProduct(PRODUCT_NAME);

        assertTrue(cartPage.isEmpty());
    }

    @Test
    @DisplayName("Кошик порожній після видалення товару")
    void cartIsEmptyAfterProductRemoval() {
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.removeProduct(PRODUCT_NAME);

        assertTrue(cartPage.isEmpty());
    }

    @Test
    @DisplayName("Користувач переходить із кошика до checkout")
    void userCanProceedFromCartToCheckout() {
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();

        cartPage.checkout();

        assertTrue(new CheckoutPage(page).isCheckoutFormOpened());
    }

    @Test
    @DisplayName("Користувач повертається з кошика до каталогу товарів")
    void userCanReturnFromCartToCatalog() {
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();

        cartPage.continueShopping();

        assertTrue(inventoryPage.isOpened());
    }
}
