package com.foo.seleniumpoc;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeleniumController {

    @GetMapping(
            value = "/capture",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] capture() {
        // https://itnext.io/how-to-run-a-headless-chrome-browser-in-selenium-webdriver-c5521bc12bf0
        // https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/7603
        // https://www.javadoc.io/doc/org.seleniumhq.selenium/selenium-api/3.141.59/index.html
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1620,1200","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        final WebDriver driver = new ChromeDriver(options);
        driver.get("https://plato.stanford.edu/");
        new WebDriverWait(driver,30).until(
                ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Archives")));
        final TakesScreenshot screenshotter =((TakesScreenshot) driver);
        final byte[] screenshot = screenshotter.getScreenshotAs(OutputType.BYTES);
        driver.close();
        driver.quit();
        return screenshot;
    }

}