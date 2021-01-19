package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends PageObjects {



    public LoginPage(WebDriver driver) {
        super(driver);
    }


    //Log In Sayfası Dogrulama

    //*[@class="forgot-password"]
    @FindBy(xpath = "//*[@class=\"forgot-password\"]")
    private WebElement loginPageValidation;
    public WebElement getLoginPageValidation(){
        return loginPageValidation;
    }




    //Sisteme Giriş Yapılması.

    @FindBy(id = "login-email")
    private WebElement email;
    @FindBy(id = "login-password-input")
    private WebElement password;
    @FindBy(xpath = "//*[@type=\"submit\"]")
    private WebElement submitButton;
    public void setEmail(){
        this.email.sendKeys(PageObjects.Email);
    }
    public void setPassword(){
        this.password.sendKeys(PageObjects.Pass);
    }
    public void setSubmitButton(){
        this.submitButton.click();
    }


    //Log In Yapıldıktan Sonra Ekranda Çıkan PopUp'ın Kapatılması İiçin Alınan Element
    @FindBy(xpath = "//*[@class=\"modal-close\"]")
    private WebElement closeAfterLoginPopUp;
    public WebElement getCloseAfterLoginPopUp(){
        return closeAfterLoginPopUp;
    }
    public void setCloseAfterLoginPopUp(){
        this.closeAfterLoginPopUp.click();
    }
}
