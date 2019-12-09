import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;

public class MavenDownload {

    static WebDriver driver;

    @BeforeClass
    public static void prepareEnvironment() {
        String systemName = System.getProperty("os.name").toLowerCase();
        if (systemName.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\bin\\chromedriver.exe");
        } else if (systemName.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver_linux");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver_osx");
        }
    }

    @Before
    public void openSite() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.get("https://maven.apache.org/download.cgi");
        driver.manage().window().maximize();
    }

    @After
    public void closeSite() {
        driver.close();
    }

    @Test
    public void successfulMavenDownload() {

        WebElement mavenVersion = driver.findElement(By.xpath("//td/a[text()=\"apache-maven-3.6.2-bin.zip\"]"));
        mavenVersion.click();
        String sourceLocation = mavenVersion.getAttribute("href");
        String maven_command = "cmd /c D:\\maven\\maven.exe -P D: --no-check-certificate " + sourceLocation;

        try {
            Process exec = Runtime.getRuntime().exec(maven_command);
            int exitVal = exec.waitFor();
            System.out.println("Exit value: " + exitVal);
        } catch (IOException ex1) {
            System.out.println(ex1.toString());
        } catch (InterruptedException ex2) {
            System.out.println(ex2.toString());
        }
    }
}

