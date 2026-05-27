package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import config.TestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import utils.ArtifactUtil;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @RegisterExtension
    FailureArtifactExtension failureArtifactExtension = new FailureArtifactExtension();

    @BeforeAll
    void startBrowser() {
        ArtifactUtil.createArtifactDirectories();
        playwright = Playwright.create();
        browser = launchBrowser();
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1440, 900));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
        page.navigate(TestConfig.BASE_URL);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (context != null) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(ArtifactUtil.tracePath(testInfo.getDisplayName())));
        }
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    void stopBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    private Browser launchBrowser() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.HEADLESS);

        return switch (TestConfig.BROWSER) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            case "chromium" -> playwright.chromium().launch(launchOptions);
            default -> throw new IllegalArgumentException("Unsupported browser: " + TestConfig.BROWSER);
        };
    }

    private class FailureArtifactExtension implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {
        @Override
        public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
            saveFailureScreenshot(extensionContext);
            throw throwable;
        }

        @Override
        public void handleBeforeEachMethodExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
            saveFailureScreenshot(extensionContext);
            throw throwable;
        }

        private void saveFailureScreenshot(ExtensionContext extensionContext) {
            if (page != null && !page.isClosed()) {
                ArtifactUtil.saveScreenshot(page, extensionContext.getDisplayName());
            }
        }
    }
}
