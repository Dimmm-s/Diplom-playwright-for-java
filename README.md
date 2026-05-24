


```bash
mvn test
```



```bash
mvn test -Dgroups=positive
mvn test -Dgroups=negative
mvn test -Dgroups=login
mvn test -Dgroups=cart
mvn test -Dgroups=checkout
mvn test -Dgroups=navigation
```



```bash
mvn test -Dbrowser=chromium
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit
```



```bash
mvn test -Dheadless=false
```



```bash
mvn test -Dgroups=checkout -Dbrowser=chromium -Dheadless=false
```

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace target/traces/имя-файла.zip"
```



```bash
mvn test
mvn test -Dgroups=positive
mvn test -Dgroups=negative
mvn test -Dgroups=login
mvn test -Dgroups=cart
mvn test -Dgroups=checkout
mvn test -Dgroups=navigation
mvn test -Dbrowser=firefox
mvn test -Dheadless=false
```
