package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TestName;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

@RunWith (DataDrivenTestRunner.class)
@DataLoader (filePaths = "InformacoesUsuarioTest.csv")

public class InformacoesUsuarioTest {

    private WebDriver navegador;

    @Rule

    public TestName test = new TestName();

    @Before

    public void setUp() {

        navegador = Web.createChrome ();

        //Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o formulário de id "signinbox" para fazer o Login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com name "Login", que está dentro do formulário de id "signinbox", o texto: "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar no campo com name "Password", que está dentro do formulário de id "signinbox", o texto: "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //Clicar no link que possui o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar em "Hi, Julio" que está dentro do elemento de class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar no link que possui o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test

    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name="mensagem")String mensagemEsperada) {

        //Clicar no botão " + ADD MORE DATA" através do XPath "//div[@id='moredata']//button[@data-target='addmoredata']"
        navegador.findElement(By.xpath("//div[@id='moredata']//button[@data-target='addmoredata']")).click();

        //Identificar o popup onde está o formulário de id "addmoredata"
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "Type" escolher a opção "Phone ou Email"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name "contact" digitar o número "+55 83 99999-9999"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //Clicar no link que possui o texto "SAVE"
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Validar que o texto é "Your contact has been added" no id "toast-container"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);

        //Aguardar 10 segundos
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));
    }

    //@Test

    public void removerContatoDeUmUsuario() {

        //Clicar no elemento com XPath "//span[text()="+5583123456789"]following-sibling::a"
        navegador.findElement(By.xpath("//span[text()=\"+5583123456789\"]/following-sibling::a")).click();

        //Confirmar a janela JavaScript
        navegador.switchTo().alert().accept();

        //Validar que o texto é "Rest in peace, dear phone!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "C:\\Users\\isabe\\Screenshot\\Taskit\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.take(navegador, screenshotArquivo);

        //Aguardar 10 segundos
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link que possui o texto "Logout"
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After

    public void tearDown() {

        //Fechar o navegador
        navegador.quit();
    }

}
