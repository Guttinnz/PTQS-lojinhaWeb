package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.LoginPage;

import java.time.Duration;

@DisplayName("Testes web do modulo de produtos")
public class ProdutosTest {

    private WebDriver navegador;

    @BeforeEach
    public void beforeEach(){
        //Abrir o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gustavo Bueno\\Desktop\\PTQS\\chromedriver_win32\\chromedriver.exe");
        this.navegador = new ChromeDriver();

        //Maximizar a tela
        this.navegador.manage().window().maximize();

        //Definir um tempo padrao de espera de 5seg
        this.navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Navegar para a pagina da lojinha web
        this.navegador.get("http://165.227.93.41/lojinha-web/v2/");
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualAZero(){

        //Fazer login
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
        //Ir para a tela de cadastro de produto
                .acessarFormularioAdicaoNovoProduto()
        //Preenche dados do produto com valor igual zero
                .informarNomeProduto("PS4")
                .informaValorProduto("000")
                .informaCoresProduto("PRETO, BRANCO")
        //Submeter o formulario com erro
                .submeterFormularioDeAdicaoComErro()
        //Validar se a mensagem de erro foi apresentada
                .capturarMensagemApresentada();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor maior que sete mil")
    public void testNaoEPermitidoRegistrarProdutoComValorMaiorASeteMil(){
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeProduto("PS4")
                .informaValorProduto("700001")
                .informaCoresProduto("PRETO, BRANCO")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos que se encontram na faixa de 00.1 e 7000.00")
    public void testAdicionarProdutoComValorDentroDaFaixa(){
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeProduto("iPhone")
                .informaValorProduto("100000")
                .informaCoresProduto("preto, vermelho, azul, rosa")
                .submeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();
        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @AfterEach
    public void afterEach(){
        //Fechar o navegador
        navegador.quit();
    }
}
