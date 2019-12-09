import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Main {

    static WebDriver driver;

    @BeforeClass
    public static void prepareEnvironment() {
        String systemName = System.getProperty("os.name").toLowerCase();
        if (systemName.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\bin\\chromedriver.exe");
        }
    }

    @Before
    public void openSite() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.get("https://marvelapp.com/");
        driver.manage().window().maximize();
    }

    @After
    public void closeSite() {
        driver.close();
    }

    @Test
    public void shouldNotLoginWithNoEmailAndWrongShortPassword() {

        WebElement signinButton = driver.findElement(By.xpath("//a[@href='/signin']"));
        signinButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement passwordField = driver.findElement(By.id("password"));
        String wrongShortPassword = "gjksf";
        passwordField.sendKeys(wrongShortPassword);

        WebElement signinButton2 = driver.findElement(By.xpath("//div[contains(text(),'Sign in')]"));
        signinButton2.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Please enter a valid email address')]")));

        Assert.assertEquals("Please enter a valid email address", driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid email address')]"))
                .getText());
        Assert.assertEquals("Must have at least 6 characters!", driver.findElement(By.xpath("//div[contains(text(),'Must have at least 6 characters!')]"))
                .getText());
        System.out.println("test1 done");
    }

    @Test
    public void successfulRegistration() {

        WebElement signUpFreeButton = driver.findElement(By.xpath("//div[contains(text(),'Sign up free')]"));
        signUpFreeButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Create your free account')]")));

        WebElement nameField = driver.findElement(By.id("name"));
        String name = "Amanda";
        nameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.id("email"));
        String generatedEmail = EmailGenerator.generateRandomEmail("Amanda", "gmail.com", 6);
        emailField.sendKeys(generatedEmail);

        WebElement passwordField = driver.findElement(By.id("password"));
        String password = "gsdfjksd";
        passwordField.sendKeys(password);

        WebElement companyField = driver.findElement(By.id("company"));
        String company = "Netguru";
        companyField.sendKeys(company);

        WebElement createButton = driver.findElement(By.xpath("//div[contains(text(),'Create your free account')]"));
        createButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Start with Free')]")));

        Assert.assertEquals(("Start with Free"), driver.findElement(By.xpath("//div[contains(text(),'Start with Free')]")).getText());
        System.out.println("test2 done");
    }

}