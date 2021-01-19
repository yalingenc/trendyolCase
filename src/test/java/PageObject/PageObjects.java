package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObjects {
    protected WebDriver driver;

    public PageObjects(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Email Login Page
    protected final static String Email = "trendyolSeleniumCase@gmail.com";
    //Password Login Page
    protected final static String Pass = "aA123456qw";





}
