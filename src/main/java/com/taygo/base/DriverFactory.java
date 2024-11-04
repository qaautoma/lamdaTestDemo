package com.taygo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;

import java.net.*;
import java.util.*;

import static java.sql.DriverManager.getDriver;

public class DriverFactory {

    protected static WebDriver driver;
    public static ThreadLocal <WebDriver> tlDriver = new ThreadLocal <> ();

    public WebDriver init_driver () throws MalformedURLException {
        // Retrieve configuration from Jenkins environment variables
        String browser = System.getenv ("BROWSER_NAME") != null ? System.getenv ("BROWSER_NAME") : "chrome";
        String os = System.getenv ("OPERATING_SYSTEM") != null ? System.getenv ("OPERATING_SYSTEM") : "Windows 11";
        String browserVersion = System.getenv ("BROWSER_VERSION") != null ? System.getenv ("BROWSER_VERSION") : "latest";
        String resolution = System.getenv ("SCREEN_RESOLUTION") != null ? System.getenv ("SCREEN_RESOLUTION") : "1920x1200";

        switch (browser.toLowerCase ()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions ();
                chromeOptions.addArguments ("--disable-notifications");
                chromeOptions.addArguments ("--use-fake-ui-for-media-stream");

                // LambdaTest specific options
                Map <String, Object> ltOptions = new HashMap <> ();
                ltOptions.put ("platformName" , os);
                ltOptions.put ("browserName" , "Chrome");
                ltOptions.put ("browserVersion" , browserVersion);
                ltOptions.put ("resolution" , resolution);
                ltOptions.put ("project" , "taygo");
                ltOptions.put ("build" , "smoke");
                ltOptions.put ("name" , "LambdaTest Chrome Test");
                chromeOptions.setCapability ("LT:Options" , ltOptions);
                URL url1 = new URL ("https://" + "pawanqa01" + ":" + "WKVCNvURDmg9zdqjMMgInFmPBg35I7bbrmnbKIZ75s12ysutM0" + "@hub.lambdatest.com/wd/hub");
                tlDriver.set (new RemoteWebDriver (url1 , chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions ();
                firefoxOptions.addArguments ("--disable-notifications");
                firefoxOptions.addArguments ("--use-fake-ui-for-media-stream");

                // LambdaTest specific options
                Map <String, Object> ltoption = new HashMap <> ();
                ltoption.put ("platformName" , os);
                ltoption.put ("browserName" , "FireFox");
                ltoption.put ("browserVersion" , browserVersion);
                ltoption.put ("resolution" , resolution);
                ltoption.put ("project" , "taygo");
                ltoption.put ("build" , "Invalid Test");
                ltoption.put ("name" , "LambdaTest Firefox Test");
                firefoxOptions.setCapability ("LT:Options" , ltoption);
                URL url2 = new URL ("https://" + "pawanqa01" + ":" + "WKVCNvURDmg9zdqjMMgInFmPBg35I7bbrmnbKIZ75s12ysutM0" + "@hub.lambdatest.com/wd/hub");
                tlDriver.set (new RemoteWebDriver (url2 , firefoxOptions));
                break;

            default:
                System.out.println ("Please pass the correct browser value: " + browser);
                break;
        }

        getDriver ().manage ().deleteAllCookies ();
        getDriver ().manage ().window ().maximize ();
        return getDriver ();
    }


    public static WebDriver getDriver () {
        return tlDriver.get ();
    }


}

//
//        switch (browser) {
//            case "chrome":
//                ChromeOptions chromeOptions = new ChromeOptions ();
//                chromeOptions.addArguments ("--disable-notifications");
//                chromeOptions.addArguments ("--use-fake-ui-for-media-stream");
//
//                // Disable popup blocking
//                chromeOptions.setExperimentalOption ("excludeSwitches" , List.of ("disable-popup-blocking"));
//
//                tlDriver.set (new ChromeDriver (chromeOptions));
//                break;
//            case "firefox":
//                FirefoxProfile profile = new FirefoxProfile ();
//                profile.setPreference ("permissions.default.microphone" , 1);
//                profile.setPreference ("dom.webnotifications.enabled" , false);
//                profile.setPreference ("signon.rememberSignons" , false);
//                FirefoxOptions firefoxOptions = new FirefoxOptions ();
//                firefoxOptions.setProfile (profile);
//                tlDriver.set (new FirefoxDriver (firefoxOptions));
//                break;
//            case "safari":
//                tlDriver.set (new SafariDriver ());
//                break;
//
//            case "browserstack":
//                ChromeOptions browserOptions = new ChromeOptions();
//                browserOptions.addArguments("--disable-notifications");
//                browserOptions.addArguments("--use-fake-ui-for-media-stream");
//
//                // Set BrowserStack options
//                Map<String, Object> browserstackOptions = new HashMap<>();
//                browserstackOptions.put("os", "Windows");
//                browserstackOptions.put("osVersion", "11");
//                browserstackOptions.put("browserName", "Chrome");
//                browserstackOptions.put("browserVersion", "latest");
//                browserstackOptions.put("projectName", "Your Project Name");
//                browserstackOptions.put("buildName", "Your Build Name");
//                browserstackOptions.put("sessionName", "BrowserStack Chrome Test");
//
//                // Attach BrowserStack capabilities to ChromeOptions
//                browserOptions.setCapability("bstack:options", browserstackOptions);
//
//                // Set the URL for BrowserStack Hub
//                URL url = new URL("https://chhotusingh_vV1UZH:SjTx823CtMXz3TDhTMyD@hub-cloud.browserstack.com/wd/hub");
//
//                // Initialize the RemoteWebDriver for BrowserStack
//                tlDriver.set(new RemoteWebDriver(url, browserOptions));
//                break;
//
//            case "lamdaTest":
//                ChromeOptions browserOptions1 = new ChromeOptions();
//                browserOptions1.addArguments("--disable-notifications");
//                browserOptions1.addArguments("--use-fake-ui-for-media-stream");
//
//                // Set LambdaTest options
//                Map<String, Object> lamdatest = new HashMap<>();
//                lamdatest.put("os", "Windows");
//                lamdatest.put("osVersion", "11");
//                lamdatest.put("browserName", "Chrome");
//                lamdatest.put("browserVersion", "latest");
//                lamdatest.put("projectName", "Your Project Name");
//                lamdatest.put("buildName", "Your Build Name");
//                lamdatest.put("sessionName", "LambdaTest Chrome Test");
//
//                // Attach LambdaTest capabilities to ChromeOptions
//                browserOptions1.setCapability("lt:options", lamdatest);
//
//                // Set the URL for LambdaTest Hub
//                URL url1 = new URL("https://" + "pawanqa01" + ":" + "WKVCNvURDmg9zdqjMMgInFmPBg35I7bbrmnbKIZ75s12ysutM0" + "@hub.lambdatest.com/wd/hub");
//
//                tlDriver.set(new RemoteWebDriver(url1, browserOptions1));
//                break;
//
//            default:
//                System.out.println ("Please pass the correct browser value: " + browser);
//                break;
//        }
//
//        getDriver ().manage ().deleteAllCookies ();
//        getDriver ().manage ().window ().maximize ();
//        return getDriver ();
//    }

//    public static WebDriver getDriver () {
//        return tlDriver.get ();
//    }

//}
