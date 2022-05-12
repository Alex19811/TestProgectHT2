import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AvicTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod(alwaysRun = true)
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    private void checkPriceOfTheGoodsIsEqualToPriceTotal() {
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Гаджети')]")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[contains(text(),'Квадрокоптери')]")).click();
        driver.findElement(By.xpath("//a[contains(@data-ecomm-cart,'237694')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String price = driver.findElement(By.xpath("//div[@class ='total-h']//span[@class ='prise']")).getText();
        String totalPrice = driver.findElement(By.xpath("//div[@class ='item-total']//span[@class ='prise']")).getText();
        Assert.assertEquals(price, totalPrice);
    }

    @Test(priority = 2)
    public void checkOutThatGoToEBooksPage() {
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Ноутбуки та планшети')]")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[contains(text(),'Електронні книги')]")).click();
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='page-title page-title-category']"));
        for (WebElement webElement : elementList)
            Assert.assertTrue(webElement.getText().contains("Електронні книги"));
    }

    @Test(priority = 3)
    private void checkElementsAmountOnSearchPageOnlyProductsInStock() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("Samsung Galaxy S22");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//label[@for='fltr-1']"));
        List<WebElement> elementList = driver.findElements(By.xpath("//span[@class='prod-cart__article']"));
        int actualElementsSize = elementList.size();
        Assert.assertEquals(actualElementsSize, 12);
    }

    @Test(priority = 4)
    private void checkThatSearchResultsContainsSearchWord() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("Samsung Galaxy");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='prod-cart__descr']"));
        for (WebElement webElement : elementList) {
            Assert.assertTrue(webElement.getText().contains("Samsung Galaxy"));
        }
    }

        @AfterMethod
        public void tearDown () {
            driver.quit();
        }
}
