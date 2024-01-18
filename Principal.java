/* ***************************************************************
* Autor............: Thiago Fernandes Pereira de Sousa
* Matricula........: 202210546
* Inicio...........: 17 de Outubro de 2023 (17/26/2023).
* Ultima alteracao.: 29 de Outubro de 2023 (29/10/2023).
* Nome.............: Jantar das Nacoes (Derivado do problema classico do Jantar Dos Filosofos)
* Funcao...........: Trabalho de um dos problemas classicos da programacao, o Jantar Dos Filosofos.
                     O problema consta com 5 filosofos sentados em uma mesa redonda, cada um com um prato de macarrao a sua frente e um garfo entre cada par de pratos.
                     Os filosofos alternam entre duas atividades: pensar e comer. Para comer, um filósofo deve pegar os dois garfos, à sua esquerda e à sua direita.
                     No entanto, como existem apenas cinco garfos no total, eles devem competir pelos garfos para comer.
                     Neste trabalho alguns elementos foram alterados, como os filosofos(agora nacoes), garfos(agora hashis) e as comidas nao sao necessariamente macarrao. 
*************************************************************** */
//Importacoes Necessarias.
import threads.Nacao;// Importando a classe de Thread Nacao.
import controller.PrincipalController;// Importando a classe de controlador PrincipalController, para interagir com a interface.
import javafx.application.Application;// Importando a classe base para iniciar a aplicacao JavaFX.
import javafx.application.Platform;// Importando a classe Platform para interacao com a plataforma JavaFX.
import javafx.fxml.FXMLLoader;// Importando a classe FXMLLoader para carregar arquivos FXML.
import javafx.scene.image.Image;// Importando a classe Image para trabalhar com imagens.
import javafx.scene.text.Font;// Importando a classe Font para manipulacao de fontes de texto.
import javafx.scene.Parent;// Importando a classe Parent, que e a raiz da hierarquia de elementos da interface grafica.
import javafx.scene.Scene;// Importando a classe Scene, que representa o conteiner principal para os elementos da interface grafica.
import javafx.stage.Stage;// Importando a classe Stage, que é a janela principal da aplicacao.


public class Principal extends Application {

/* ***************************************************************
* Metodo: start
* Funcao: Ponto de entrada da aplicacao JavaFX. Pode lançar uma excecao "IOException".
* Parametros: stage (Do tipo array de "Stage", que representa a janela principal da aplicacao).
* Retorno: nenhum (void).
*************************************************************** */
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/TelaController.fxml"));// carregando o arquivo fisico e carregando estrutura.
    Scene scene = new Scene(root);// criando uma cena.
    stage.setScene(scene);// atribui cena para a janela.
    stage.setWidth(606);// setando o tamanho da janela.
    stage.setHeight(435);// setando o tamanho da janela.
    stage.setResizable(false);// proibindo o usuario de redimencionar tela.
    stage.setTitle("Jantar Das Nacoes");// configurando o titulo da tela.
    scene.getStylesheets().add("view/Estilo.css");// carregando o arquivo css.
    stage.getIcons().add(new Image("/img/Icon.png"));// carregando uma imagem e atribuindo um icon na janela.
    Font.loadFont(getClass().getResourceAsStream("/img/fonte/herculanum.ttf"), 10);// carregando a fonte para ser utilizada
    stage.setOnCloseRequest(t -> {// Define o comportamento de fechamento da janela.
      Platform.exit();// Encerra a plataforma JavaFX.
      System.exit(0);// Encerra o aplicativo Java.
    });
    stage.show();// mostrando janela.
  }

/* ***************************************************************
* Metodo: main
* Funcao: Ponto de entrada da aplicacao Java.
* Parametros: args (Do tipo array de "strings", que pode conter argumentos da linha de comando).
* Retorno: nenhum (void).
*************************************************************** */
  public static void main(String[] args) {
    launch(args);
  }
}