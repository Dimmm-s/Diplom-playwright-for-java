package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage {
    private final Locator title;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalCodeInput;
    private final Locator continueButton;
    private final Locator finishButton;
    private final Locator errorMessage;
    private final Locator completeHeader;

    public CheckoutPage(Page page) {
        this.title = page.locator("[data-test='title']");
        this.firstNameInput = page.locator("[data-test='firstName']");
        this.lastNameInput = page.locator("[data-test='lastName']");
        this.postalCodeInput = page.locator("[data-test='postalCode']");
        this.continueButton = page.locator("[data-test='continue']");
        this.finishButton = page.locator("[data-test='finish']");
        this.errorMessage = page.locator("[data-test='error']");
        this.completeHeader = page.locator("[data-test='complete-header']");
    }

    public boolean isCheckoutFormOpened() {
        return title.isVisible() && "Checkout: Your Information".equals(title.textContent().trim());
    }

    public void fillFirstName(String firstName) {
        firstNameInput.fill(firstName);
    }

    public void fillLastName(String lastName) {
        lastNameInput.fill(lastName);
    }

    public void fillPostalCode(String postalCode) {
        postalCodeInput.fill(postalCode);
    }

    public void submitForm() {
        continueButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.textContent().trim();
    }

    public void finishOrder() {
        finishButton.click();
    }

    public boolean isOrderComplete() {
        return completeHeader.isVisible() && "Thank you for your order!".equals(completeHeader.textContent().trim());
    }
}
