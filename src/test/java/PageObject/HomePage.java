package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class HomePage extends PageObjects{


    public HomePage(WebDriver driver) {
        super(driver);
    }



    //Pop Up Kapatma

    @FindBy(xpath = "//*[@class=\"fancybox-item fancybox-close\"]")
    private WebElement popUp;
    public void setPopUp(){
        this.popUp.click();
    }



    //Trendyol logosu içersisinde bulunan text kısmından trenyol içeriğinin
    //çekilmesi ve açılan sayfa kontrolü yapılması

    private final String actualTitle = "En Trend Ürünler Türkiye'nin Online Alışveriş Sitesi Trendyol'da";
    private final String expectedResult = driver.getTitle();
    public String getActualTitle(){
        return actualTitle;
    }
    public String getExpectedResult(){
        return expectedResult;
    }



    //Login Sayfasının Açılması

    // Mouse Over için Action oluşturulması.
    Actions action = new Actions(driver);
    //Mouse'un üzerine gelip bekleyeceği elementin class name değeri ile bulunması
    // ve fonsiyon yaratılarak çağırıldığında tıklanmaı
    @FindBy(xpath = "//*[@id=\"account-navigation-container\"]/*[contains(@class, 'account-navigation-wrapper')]/*[contains(@class, 'account-nav-item user-login-container')]")
    private WebElement mouseOver;
    @FindBy(className = "login-button")
    private WebElement mouseOverClick;
    public void setMouseOverClick(){
        this.action.moveToElement(mouseOver).moveToElement(mouseOverClick).click().build().perform();

    }





    //Üst Tab Elemanlarının Listede Toplanması
    @FindBy(xpath = "//*[@class=\"main-nav\"]/li")
    private List<WebElement> tabPaths;
    public List<WebElement> getTabPaths(){
        return tabPaths;
    }

    //butikImageKontrol metodu için sayfadaki big list'in alınması
    @FindBy(xpath = "//*[@class=\"component-list component-big-list\"]/*")
    private List<WebElement> componentBigList;
    public List<WebElement> getComponentBigList(){
        return componentBigList;
    }
    //butikImageKontrol metodu için sayfadaki small list'in alınması
    @FindBy(xpath = "//*[@class=\"component-list component-small-list\"]/*")
    private List<WebElement> componentSmallList;
    public List<WebElement> getComponentSmallList(){
        return componentSmallList;
    }
    //butikImageKontrol metodu için sayfadaki banner-wrapper altında bulunan elemanların alınması
    @FindBy(xpath = "//*[@id=\"banners-wrapper\"]/*")
    private List<WebElement> bannersWrapper;
    public List<WebElement> getBannersWrapper(){
        return bannersWrapper;
    }

    //Rastgele Numara Seçilmesi ve Xpath içinde TestCase class'ına gönderilmesi.
    Random random = new Random();
    int randomNum = random.nextInt((getComponentBigList().size() - 1)+1)+1;
    public int getRandomNum(){
        return randomNum;
    }

    //Getter fonksiyonu butik seçimi için.
    public WebElement rastgeleButikSecim(){
        return (driver.findElement(By.xpath("//*[@class=\"component-list component-big-list\"]/article["+randomNum+"]")));
    }



    public void butikDogrulanmasıIcınTumSayfanınYuklenmesi(){
        // Tüm butiklerin yüklenmesi için sayfanın yüklenmesi bitene kadar sayfanın en alt kısmına gidilir.
        try {
            long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000);

                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    // Butik yüklenme kontrolü.
    public void butikImageKontrol() throws InterruptedException {

        //Tüm sayfadaki butikleri yüklendikten sonra sırasıyla,
        //Big List içerisinde bulunanlar
        for (int i=1; i<=getComponentBigList().size(); i++){

            try {
                driver.findElement(By.xpath("//*[@class=\"component-list component-big-list\"]/article["+i+"]/*[contains (@data-tracker, 'seen:homepage-component')]")).isDisplayed();
            } catch (Exception e) {
                System.out.println("Big List İçerisindeki Butik Yüklenemedi.");
            }
        }


        // Small List içerisinde bulunanlar
        for (int i=1; i<=getComponentSmallList().size(); i++){

            try {
                driver.findElement(By.xpath("//*[@class=\"component-list component-small-list\"]/article["+i+"]/*[contains (@data-tracker, 'seen:homepage-component')]")).isDisplayed();
            }catch (Exception e) {
                System.out.println("Small List İçerisindeki Butik Yüklenemedi.");
            }


        }

        //Side bar'da bannerWrapper id'si adı altında bulunan butiklerin yüklenme kontrolü.
        for (int i=1; i<=getBannersWrapper().size(); i++){

            try {
                driver.findElement(By.xpath("//*[@id=\"banners-wrapper\"]/div["+i+"]")).isDisplayed();
            } catch (Exception e) {
                System.out.println("Side Bar İçerisindeki Butik Yüklenemedi.");
            }
        }


    }






}
