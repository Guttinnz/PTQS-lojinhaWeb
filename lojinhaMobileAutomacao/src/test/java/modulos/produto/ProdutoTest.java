package modulos.produto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import telas.LoginTela;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@DisplayName("Testes Mobile do Módulo de Produto")
public class ProdutoTest {

    private WebDriver app;

    @BeforeEach
    public void BeforeEach ()  throws MalformedURLException {
        // Abrir o App
        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("deviceName", "Google Pixel 3");
        capacidades.setCapability("platformName", "Android");
        capacidades.setCapability("udid", "192.168.225.101:5555");
        capacidades.setCapability("appPackage", "com.lojinha");
        capacidades.setCapability("appActivity", "com.lojinha.ui.MainActivity");
        capacidades.setCapability("app", "C:\\Users\\Gustavo Bueno\\Desktop\\PTQS\\Lojinha+Android+Nativa\\lojinha-nativa.apk");

        this.app = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capacidades);
        this.app.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @DisplayName("Validação do Valor do Produto Não Permitido")
    @Test
    public void testValidacaoDoValorDoProdutoNaoPermitido() {
        //Fazer Login
        String mensagemApresentada = new LoginTela(app)
            .preencherUsuario("admin")
            .preencherSenha("admin")
            .submeterLogin()
        // Abrir o formulario de novo produto
            .abrirTelaAdicaoProduto()
        // Cadastrar um produto com valor invalido
            .preencherNomeProduto("Iphone")
            .preencherValorPorduto("700001")
            .preencherCoresProduto("branco")
            .submissaoComErro()
            .obterMensagemComErro();
        // Validar que a mensagem do valor inválido foi apresentada
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @AfterEach
            public void AfterEach() {
                app.quit();
        }
}
