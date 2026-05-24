package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.InventoryPage;
import pages.LoginPage;

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
}
