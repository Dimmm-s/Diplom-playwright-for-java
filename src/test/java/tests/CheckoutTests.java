package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("checkout")
public class CheckoutTests extends BaseTest {
    private CheckoutPage checkoutPage;

    @BeforeEach
    void openCheckout() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.openCart();
        new CartPage(page).checkout();
        checkoutPage = new CheckoutPage(page);
    }

    @Test
    @Tag("positive")
    @DisplayName("Успішне оформлення замовлення з валідними даними")
    void userCanCompleteCheckoutWithValidData() {
        checkoutPage.fillFirstName("Ivan");
        checkoutPage.fillLastName("Petrenko");
        checkoutPage.fillPostalCode("01001");
        checkoutPage.submitForm();
        checkoutPage.finishOrder();

        assertTrue(checkoutPage.isOrderComplete());
    }

    @ParameterizedTest(name = "{index}: {3}")
    @CsvSource({
            "'', Petrenko, 01001, Error: First Name is required",
            "Ivan, '', 01001, Error: Last Name is required",
            "Ivan, Petrenko, '', Error: Postal Code is required"
    })
    @Tag("negative")
    @DisplayName("Перевірка помилок checkout-форми")
    void checkoutRequiredFieldsValidation(String firstName, String lastName, String postalCode, String expectedError) {
        checkoutPage.fillFirstName(firstName);
        checkoutPage.fillLastName(lastName);
        checkoutPage.fillPostalCode(postalCode);
        checkoutPage.submitForm();

        assertEquals(expectedError, checkoutPage.getErrorMessage());
    }
}
