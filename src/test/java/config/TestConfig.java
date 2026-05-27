package config;

import java.nio.file.Path;

public final class TestConfig {
    public static final String BASE_URL = "https://www.saucedemo.com/";
    public static final String BROWSER = System.getProperty("browser", "chromium").toLowerCase();
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "true"));
    public static final Path SCREENSHOTS_DIR = Path.of("target", "screenshots");
    public static final Path TRACES_DIR = Path.of("target", "traces");

    private TestConfig() {
    }
}
