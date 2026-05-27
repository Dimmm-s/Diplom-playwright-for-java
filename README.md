# SauceDemo Playwright for Java

Автоматизований тестовий проєкт для перевірки веб-застосунку SauceDemo засобами Playwright for Java, JUnit 5 та Maven.

## Структура проєкту

```text
src/test/java
├── base       # життєвий цикл Playwright, Browser, BrowserContext, Page
├── config     # базова URL-адреса, браузер, режим headless, шляхи артефактів
├── pages      # Page Object класи: LoginPage, BasePage, Menu, InventoryPage, ProductPage, CartPage, CheckoutPage
├── tests      # тестові сценарії за групами перевірок
└── utils      # робота зі скриншотами та trace-архівами
```

## Повний запуск

```bash
mvn test
```

## Запуск за групами

```bash
mvn test -Dgroups=positive
mvn test -Dgroups=negative
mvn test -Dgroups=login
mvn test -Dgroups=access
mvn test -Dgroups=inventory
mvn test -Dgroups=cart
mvn test -Dgroups=checkout
mvn test -Dgroups=navigation
mvn test -Dgroups=product
mvn test -Dgroups=problem-users
```

## Запуск у різних браузерах

```bash
mvn test -Dbrowser=chromium
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit
```

## Запуск у видимому режимі

```bash
mvn test -Dheadless=false
```

## Приклади комбінованого запуску

```bash
mvn test -Dgroups=checkout -Dbrowser=chromium -Dheadless=false
mvn test -Dgroups=problem-users -Dbrowser=firefox
```

## Артефакти

Після виконання тестів проєкт формує:

- `target/screenshots` - скриншоти для помилкових сценаріїв;
- `target/traces` - trace-архіви Playwright для аналізу виконання.

Відкрити trace можна командою:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace target/traces/name-file.zip"
```
