package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.InventoryPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("login")
public class LoginTests extends BaseTest {
    @Test
    @Tag("positive")
    @DisplayName("Успішна авторизація користувача standard_user")
    void standardUserCanLogin() {
        LoginPage loginPage = new LoginPage(page);
        InventoryPage inventoryPage = new InventoryPage(page);

        loginPage.login("standard_user", "secret_sauce");

        assertTrue(inventoryPage.isOpened());
        assertThat(page.locator("[data-test='title']")).hasText("Products");
    }

    @ParameterizedTest(name = "{index}: {2}")
    @CsvSource(value = {
            "standard_user|wrong_password|Epic sadface: Username and password do not match any user in this service",
            "''|secret_sauce|Epic sadface: Username is required",
            "standard_user|''|Epic sadface: Password is required",
            "locked_out_user|secret_sauce|Epic sadface: Sorry, this user has been locked out."
    }, delimiter = '|')
    @Tag("negative")
    @DisplayName("Перевірка помилок авторизації")
    void loginValidationErrorsAreDisplayed(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(page);

        loginPage.login(username, password);

        assertEquals(expectedError, loginPage.getErrorMessage());
    }

    @ParameterizedTest(name = "Захищений маршрут /{0} недоступний без входу")
    @ValueSource(strings = {"inventory.html", "cart.html", "checkout-step-one.html"})
    @Tag("access")
    @Tag("negative")
    @DisplayName("Внутрішні сторінки недоступні без авторизації")
    void protectedPagesRequireAuthentication(String protectedPath) {
        LoginPage loginPage = new LoginPage(page);

        loginPage.openProtectedPath(protectedPath);

        assertTrue(loginPage.isOpened());
        assertTrue(loginPage.getErrorMessage().contains("You can only access '/" + protectedPath + "'"));
    }
}
