package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.TestConfig;

public class LoginPage {
    private final Page page;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.usernameInput = page.locator("[data-test='username']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-button']");
        this.errorMessage = page.locator("[data-test='error']");
    }

    public void open() {
        page.navigate(TestConfig.BASE_URL);
    }

    public void openProtectedPath(String path) {
        page.navigate(TestConfig.BASE_URL + path.replaceFirst("^/", ""));
    }

    public void login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.textContent().trim();
    }

    public boolean isOpened() {
        return usernameInput.isVisible() && passwordInput.isVisible() && loginButton.isVisible();
    }

    public String currentUrl() {
        return page.url();
    }
}
