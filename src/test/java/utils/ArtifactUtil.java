package utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import config.TestConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ArtifactUtil {
    private static final DateTimeFormatter FILE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private ArtifactUtil() {
    }

    public static void createArtifactDirectories() {
        createDirectory(TestConfig.SCREENSHOTS_DIR);
        createDirectory(TestConfig.TRACES_DIR);
    }

    public static String safeFileName(String testName) {
        String normalizedName = testName == null || testName.isBlank() ? "test" : testName;
        return normalizedName
                .replaceAll("[^a-zA-Z0-9а-яА-ЯіІїЇєЄґҐ._-]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
    }

    public static Path saveScreenshot(Page page, String testName) {
        createDirectory(TestConfig.SCREENSHOTS_DIR);
        Path screenshotPath = TestConfig.SCREENSHOTS_DIR.resolve(fileName(testName, "png"));
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(screenshotPath)
                .setFullPage(true)
                .setType(ScreenshotType.PNG));
        return screenshotPath;
    }

    public static Path tracePath(String testName) {
        createDirectory(TestConfig.TRACES_DIR);
        return TestConfig.TRACES_DIR.resolve(fileName(testName, "zip"));
    }

    private static String fileName(String testName, String extension) {
        return safeFileName(testName) + "-" + LocalDateTime.now().format(FILE_TIME_FORMAT) + "." + extension;
    }

    private static void createDirectory(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create artifact directory: " + directory, e);
        }
    }
}
