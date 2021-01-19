package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SecilenButikSayfası extends PageObjects{


    public SecilenButikSayfası(WebDriver driver) {
        super(driver);
    }


    //butikImageKontrol metodu için sayfadaki banner-wrapper altında bulunan elemanların alınması
    @FindBy(xpath = "//*[@class=\"products\"]/div")
    private List<WebElement> butikGorsel;
    public List<WebElement> getButikGorsel(){
        return butikGorsel;
    }

    //Rastgele Numara Seçilmesi ilgili Sayfanın toplam eleman sayısına göre.
    Random random = new Random();
    int randomNum = random.nextInt((getButikGorsel().size() - 1)+1)+1;
    public int getRandomNum(){
        return randomNum;
    }



    //Getter fonksiyonu Ürün seçimi için.
    public WebElement rastgeleÜrünSeçimi(){
        return (driver.findElement(By.xpath("//*[@class=\"products\"]/div["+getRandomNum()+"]/a/*[contains (@class, 'description fixed-elements')]/div/*[contains(@class,'name')]")));
    }


    //Doğru Ürünün ve Sayfanın Açıldığının Kontrolü
    @FindBy(xpath = "/html/head/*[contains(@name, 'twitter:title')]")
        private WebElement urunDogrulama;
    public WebElement getUrunDogrulama(){
        return urunDogrulama;
    }




    // Butik Gorsel kontrolü.
    public void butikGorselKontrol() throws InterruptedException {
        //Tüm sayfadaki butikleri yüklendikten sonra sırasıyla,
        for (int i=1; i<getButikGorsel().size(); i++){
            try {
                driver.findElement(By.xpath("//*[@class=\"products\"]/div["+i+"]/a/*[contains (@class, 'image-container')]")).isDisplayed();
            } catch (Exception e) {
                System.out.println("Rastgele Seçilen Butik İçerisinde Yüklenmeyen Görsel");
            }
        }

        }





}
