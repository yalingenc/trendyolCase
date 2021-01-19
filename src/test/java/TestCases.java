import PageObject.HomePage;
import PageObject.LoginPage;
import PageObject.SecilenButikSayfası;
import PageObject.SecilenUrunSayfası;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;


public class TestCases{

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static FirefoxProfile profile;

    @BeforeClass
    public void openBrowser() {

        //Parameter denedim cros platform olması için ama testng.xml dosyası ile ilgili
        // sorun yaşadım ve çalıştıramadım bu sebepten dolayı yapamadım. Parametre olarka xml içerisinde tanımlayarak


        System.setProperty("webdriver.gecko.driver", Utils.FIREFOX_DRIVER_LOCATION);

        //Olabilecek notificationların önüne geçebilmek için aşağıdaki profile oluşturuldu ve aşağıdaki adımlarla engellendi.
        profile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        profile.setPreference("permissions.default.desktop-notification", 1);
        driver = new FirefoxDriver(capabilities);

        //Site URL'si ile bağlanma.
        driver.get(Utils.BASE_URL);
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    //Sİte ilk açıldığında çıkan PopUp'ın kapatılması.
    @Test(priority = 1)
    public void popUpKapatılması() throws InterruptedException {
        try {
            HomePage homePage = new HomePage(driver);
            homePage.setPopUp();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    //Açılan sayfanın Trendyol olduğu doğrulanıyor.
    @Test(priority = 2)
    public void trendyolAcılısSayfasıDogrulama() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String actualResult = homePage.getActualTitle();
        String expectedResult = homePage.getExpectedResult();
        Assert.assertEquals(actualResult, expectedResult);
        Thread.sleep(2000);
    }


    //Mouse Over ile kullanıcı girişi için, Giriş Yap butonuna tıklanması.
    @Test(priority = 3)
    public void mouseOver() throws InterruptedException {
        try {
            HomePage homePage = new HomePage(driver);
            homePage.setMouseOverClick();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }


    //Login Page Doğrulama
    @Test(priority = 4)
    public void clickLogin() {
        wait = new WebDriverWait(driver, 10);
        LoginPage loginPage = new LoginPage(driver);
        try {
            wait.until(ExpectedConditions.visibilityOf(loginPage.getLoginPageValidation()));
        } catch (Exception e) {
            throw e;
        }
    }


    //Sisteme Email ve Şifre ile Giriş Yapılması.
    @Test(priority = 5)
    public void setEmailPassword() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        try {
            loginPage.setEmail();
            loginPage.setPassword();
            Thread.sleep(2000);
            loginPage.setSubmitButton();
        } catch (Exception e) {
            throw e;
        }
    }


    //Login İşlemleri
    @Test(priority = 6)
    public void closeAfterLogInPopUpAndDont() {
        LoginPage loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, 10);
        try {
            //WebElement görünür olana kadar bekleniyor.
            wait.until(ExpectedConditions.visibilityOf(loginPage.getCloseAfterLoginPopUp()));
            //WebElement(PopUp) kapatılıyor.
            loginPage.setCloseAfterLoginPopUp();
        }catch (Exception e) {
            throw e;
        }
    }



    //Tüm Tablardaki Butik Kontrolü.
    @Test(priority = 7)
    public void tabClick() throws InterruptedException {

        HomePage homePage = new HomePage(driver);

        try {
            List<WebElement> tabElements = homePage.getTabPaths();
            for (int i = 1; i <= tabElements.size(); i++) {
                //Web Element'i burada tanımlamak zorunda kaldım çünkü, Liste halinde çektiğim
                //Elementlerden birine tıkladığımda yeniden dönüp aynı XPATH'i bulamadı yeniden location vermek zorundaydım
                //Bir şekilde POM yapısını sürdürebilmek için çabaladım ama bu aşamada takıldım.
                driver.findElement(By.xpath("//*[@id=\"navigation-wrapper\"]/nav/ul/li[" + i + "]")).click();
                Thread.sleep(2000);

                //Sayfanın Tüm Butiklerinin Yüklenmesi için Aşağıdaki Metod Yaratıldı.
                homePage.butikDogrulanmasıIcınTumSayfanınYuklenmesi();
                //Her Sayfanın Butik Kontrolü İçin Aşağıdaki Metod Yaratıldı.
                homePage.butikImageKontrol();
            }
        }
        catch(Exception e){
                System.out.println("Üst Kategori Tabları Seçilirken Sorun Oluştu.");
        }

    }

    //Rastgele Butik Seçimi
    @Test(priority = 8)
    public void rastgeleBUtikSecimi() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        try {
            //Rastgele Seçilen Tab'a Gidilmesi.
            homePage.rastgeleButikSecim().click();

            Thread.sleep(2000);
        }catch (Exception e){
            throw e;
        }
    }


    //Rastgele Butik Seçim Sayfası Görsel Doğrulama
    @Test(priority = 9)
    public void bUtikSecimDogrulama() throws InterruptedException {

        SecilenButikSayfası secilenButikSayfası = new SecilenButikSayfası(driver);

        //Aşağıdaki Metod Yorum Satırına Alındı Çünkü Binlerce Görsel olma İhtimali olduğı için.
        //homePage.butikDogrulanmasıIcınTumSayfanınYuklenmesi();
        Thread.sleep(2000);
        //Sadece İlk Açıldığında Div İçerisinde bulunan Ürün GÖrsellerinin Kontrolü Yapılıyor.
        secilenButikSayfası.butikGorselKontrol();

    }


    //Rastgele Ürün Seçimi
    @Test(priority = 10)
    public void urunSecimi() throws InterruptedException {

        SecilenButikSayfası secilenButikSayfası = new SecilenButikSayfası(driver);

        try {
            Thread.sleep(2000);
            //Seçilecek olan ürünün içerdiği text'i alıyorum
            String urunBilgisi = secilenButikSayfası.rastgeleÜrünSeçimi().getText();
            System.out.println(urunBilgisi);
            ////Ürüne tıklayıp içine giriyorum
            secilenButikSayfası.rastgeleÜrünSeçimi().click();
            System.out.println("Click Sonrası : "+ secilenButikSayfası.getUrunDogrulama().getAttribute("content"));
            //Seçmeden önce aldığım text ile ürün sayfası açıldıktan sonra aldığım text'i karşılaştırıyorum
            //en stabil verinin twitter title'ında olduğunu gördüğüm için onu kullandım. Ama bazen
            // yanlış sonuç verebiliyor daha sağlam bir nokta bulamadım doğrulama için
            Assert.assertEquals(secilenButikSayfası.getUrunDogrulama().getAttribute("content"), urunBilgisi);


        }catch (Exception e){
            throw e;
        }

    }



    //Sepete Eklenmesi
    @Test(priority = 11)
    public void sepeteEkle() throws InterruptedException {

        SecilenUrunSayfası secilenUrunSayfası = new SecilenUrunSayfası(driver);
        Thread.sleep(2000);
        secilenUrunSayfası.getSepeteEkle().click();

    }



    @AfterClass
    public void closeBrowser(){
        driver.close();
    }

}
