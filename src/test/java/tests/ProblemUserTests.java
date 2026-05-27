package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("negative")
@Tag("problem-users")
public class ProblemUserTests extends BaseTest {
    @Test
    @Tag("problem-user")
    @DisplayName("problem_user відображає некоректні зображення товарів")
    void problemUserCatalogHasBrokenProductImages() {
        InventoryPage inventoryPage = loginAs("problem_user");

        assertTrue(inventoryPage.isOpened());
        assertTrue(inventoryPage.hasProblemPlaceholderImages());
    }

    @Test
    @Tag("problem-user")
    @DisplayName("problem_user не може видалити товар з каталогу")
    void problemUserCannotRemoveProductFromCatalog() {
        InventoryPage inventoryPage = loginAs("problem_user");

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.removeProductFromCatalog("Sauce Labs Backpack");

        assertEquals(1, inventoryPage.getCartBadgeCount());
    }

    @Test
    @Tag("performance")
    @DisplayName("performance_glitch_user відкриває каталог після затримки")
    void performanceGlitchUserCanOpenCatalogAfterDelay() {
        InventoryPage inventoryPage = loginAs("performance_glitch_user");

        assertTrue(inventoryPage.isOpened());
    }

    @ParameterizedTest(name = "{0} може бути використаний для негативних перевірок")
    @ValueSource(strings = {"error_user", "visual_user"})
    @Tag("problem-user")
    @DisplayName("Додаткові проблемні користувачі авторизуються у застосунку")
    void additionalProblemUsersCanBeUsedForNegativeScenarios(String username) {
        InventoryPage inventoryPage = loginAs(username);

        assertTrue(inventoryPage.isOpened());
    }

    private InventoryPage loginAs(String username) {
        new LoginPage(page).login(username, "secret_sauce");
        return new InventoryPage(page);
    }
}
