package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class SecilenUrunSayfası extends PageObjects{


    public SecilenUrunSayfası(WebDriver driver) {
        super(driver);
    }


    //Ürünün Sepete Eklenmesi
    @FindBy(xpath = "//*[@class=\"add-to-bs-wrp\"]/button")
    private WebElement sepeteEkle;
    public WebElement getSepeteEkle(){
        return sepeteEkle;
    }



}
