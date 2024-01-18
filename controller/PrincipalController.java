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
package controller;

//Importacoes Necessarias.
import threads.Nacao;// Importando a classe de Thread Nacao.
import java.net.URL;// Importacao necessaria para trabalhar com URLs, que podem ser uteis para carregar recursos ou interagir com a internet.
import java.util.ResourceBundle;// Importacao necessaria para trabalhar com pacotes de recursos (resource bundles), que sao usados para internacionalizar aplicativos.
import java.util.concurrent.Semaphore;// Importacao da classe Semaphore para lidar com semaforos.
import javafx.event.ActionEvent;// Importacao para a classe 'ActionEvent' do JavaFX, usada para lidar com eventos de açao, como cliques de botao.
import javafx.fxml.FXML;// Importacao para a anotacao 'FXML', usada para injetar elementos do arquivo FXML no codigo Java.
import javafx.fxml.Initializable;// Importacao para a interface 'Initializable', que requer a implementação de um metodo 'initialize'.
import javafx.scene.control.Button;// Importacao para a classe 'Button' do JavaFX, usada para criar botoes clicaveis.
import javafx.scene.control.Slider;// Importacao para a classe 'Slider' do JavaFX, usada para criar controles deslizantes.
import javafx.scene.image.Image;// Importacao para a classe 'Image' do JavaFX, usada para trabalhar com imagens.
import javafx.scene.image.ImageView;// Importacao para a classe 'ImageView' do JavaFX, usada para exibir imagens em uma interface grafica.
import javafx.scene.layout.AnchorPane;// Importacao para a classe 'AnchorPane' do JavaFX, que e um layout que permite ancorar elementos de interface em relacao a bordas ou outros elementos.
import javafx.scene.media.Media;//Importacao para a classe 'Media', que representa um recurso de midia, como um arquivo de audio ou video.
import javafx.scene.media.MediaPlayer;//Importacao para a classe 'MediaPlayer', e usada para reproduzir e controlar midia.
import java.io.File;//Importacao para a classe 'File', e usada para trabalhar com arquivos e diretorios no sistema de arquivos local. 

public class PrincipalController implements Initializable {
  //---------------------------------- ATRIBUTOS ----------------------------------//
  //AnchorPane
  @FXML
  private AnchorPane anchorPanePrincipal, anchorPaneInicial, anchorPaneJantar;

  //ImageView
  @FXML
  private ImageView imagemInicial;
  @FXML
  private ImageView imagemPlay;
  @FXML
  private ImageView imagemJantarDasNacoes;
  @FXML
  private ImageView imagemJantar;
  @FXML
  private ImageView hashi0, hashi1, hashi2, hashi3, hashi4;
  @FXML
  private ImageView imagemComidaAang, imagemComidaSokka, imagemComidaZuko, imagemComidaToph, imagemComidaKatara;
  @FXML
  private ImageView imagemPensamentoAang, imagemPensamentoSokka, imagemPensamentoZuko, imagemPensamentoToph, imagemPensamentoKatara;
  @FXML
  private ImageView aangEsperando, sokkaEsperando, zukoEsperando, tophEsperando, kataraEsperando;
  @FXML
  private ImageView imagemHashiUsandoAang, imagemHashiUsandoSokka, imagemHashiUsandoZuko, imagemHashiUsandoToph, imagemHashiUsandoKatara;
  @FXML
  private ImageView imgBtnPauseAang, imgBtnPauseSokka, imgBtnPauseZuko, imgBtnPauseKatara, imgBtnPauseToph;
  @FXML
  private ImageView imgBtnSom;

  //Slider
  @FXML
  private Slider sliderVelocidadePensando0, sliderVelocidadePensando1, sliderVelocidadePensando2, sliderVelocidadePensando3, sliderVelocidadePensando4;
  @FXML
  private Slider sliderVelocidadeComendo0, sliderVelocidadeComendo1, sliderVelocidadeComendo2, sliderVelocidadeComendo3, sliderVelocidadeComendo4;
  private Slider[] sliderPensar;
  private Slider[] sliderComer;

  //Button
  @FXML
  private Button btnIniciar;
  @FXML
  private Button BtnAang, BtnSokka, BtnZuko, BtnKatara, BtnToph;
  @FXML
  private Button BtnReset;
  @FXML
  private Button btnSom;

  //Som
  String trilha = "img/trilha/Avatar_Theme.mp3";// Caminho para o mp3.
  Media media = new Media(new File(trilha).toURI().toString());// Arquivo de audio.
  MediaPlayer mediaPlayer = new MediaPlayer(media);// MediaPlayer do arquivo de audio.

  //Thread
  private static final Nacao[] nacao = new Nacao[5];//Declara um array de objetos do tipo Nacao que representa as nacoes na simulacao.
  
/* ***************************************************************
* Metodo: initialize
* Funcao: Inicializa a interface e define a visibilidade inicial dos elementos.
* Parametros: location (URL do local do arquivo de FXML), resources (Pacote de recursos).
* Retorno: nenhum (void).
*************************************************************** */
  @Override
  public void initialize(URL location, ResourceBundle resource) {
    anchorPaneInicial.setVisible(true);
    anchorPaneJantar.setVisible(false);
    mediaPlayer.play();//Inicia o som.
    mediaPlayer.setVolume(0.25);//Define o volume padrao(25%).
    mediaPlayer.setOnEndOfMedia(() -> {//Observa quando o som chegar ao fim.
    	mediaPlayer.seek(mediaPlayer.getStartTime());//Define o tempo de reprodução de volta para o início (0)
    	mediaPlayer.play();//Inicia a reprodução novamente
    });
  }

  @FXML
  private int contSom = 0;//Contador relacionado com a mudanca de imagem do botao de volume.

/* ***************************************************************
* Metodo: som.
* Funcao: Muda a imagem do botao de volume dependendo do contSom.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: Nenhum.
*************************************************************** */
  @FXML
  void som(ActionEvent event) {
  	Image imagem;
  	contSom++;//incrementa contSom.
  	//Verifica se o contador e par ou impar.
  	if (contSom % 2 == 0) {
      imagem = new Image("img/desmudo.png");//Se o contador cont for par, ele atribui a imagem "img/desmudo.png" a variavel imagem.
      mediaPlayer.setVolume(0.25);//Seta o volume para 25%(PADRAO).
    } else {
      imagem = new Image("img/mudo.png");//Se o contador cont for par, ele atribui a imagem "img/mudo.png" a variavel imagem.
       mediaPlayer.setVolume(0);//Seta o volume para mudo.
    }
    imgBtnSom.setImage(imagem);//Seta a imagem de acordo o necessario.
  }

/* ***************************************************************
* Metodo: CliqueEmIniciar.
* Funcao: Iniciar a simulacao do "Jantar das Nacoes" quando acionado pelo evento de clique do mouse.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void CliqueEmIniciar(ActionEvent event) {
    anchorPaneInicial.setVisible(false);//Torna o painel invisivel.
    anchorPaneJantar.setVisible(true);//Torna o painel visivel.

    //Cria array de controle deslizante para controlar a velocidade de pensar de cada "nacao" na simulacao.
    Slider slidersPensar[] = { sliderVelocidadePensando0, sliderVelocidadePensando1, sliderVelocidadePensando2, sliderVelocidadePensando3, sliderVelocidadePensando4 };
    sliderPensar = slidersPensar;

    //Cria array de controle deslizante para controlar a velocidade de comer de cada "nacao" na simulacao.
    Slider slidersComer[] = { sliderVelocidadeComendo0, sliderVelocidadeComendo1, sliderVelocidadeComendo2, sliderVelocidadeComendo3, sliderVelocidadeComendo4 };
    sliderComer = slidersComer;

    //Inicializa e inicia cinco threads (representando as "nacoes") com os controles deslizantes correspondentes.
    nacao[0] = new Nacao(0, this, sliderPensar[0], sliderComer[0]);
    Nacao.arraySemaphores[0] = new Semaphore(0);
    nacao[0].start();

    nacao[1] = new Nacao(1, this, sliderPensar[1], sliderComer[1]);
    Nacao.arraySemaphores[1] = new Semaphore(0);
    nacao[1].start();

    nacao[2] = new Nacao(2, this, sliderPensar[2], sliderComer[2]);
    Nacao.arraySemaphores[2] = new Semaphore(0);
    nacao[2].start();

    nacao[3] = new Nacao(3, this, sliderPensar[3], sliderComer[3]);
    Nacao.arraySemaphores[3] = new Semaphore(0);
    nacao[3].start();

    nacao[4] = new Nacao(4, this, sliderPensar[4], sliderComer[4]);
    Nacao.arraySemaphores[4] = new Semaphore(0);
    nacao[4].start();

    //Habilita cinco botoes que representam as "nacoes".
    BtnAang.setDisable(false);
    BtnSokka.setDisable(false);
    BtnZuko.setDisable(false);
    BtnToph.setDisable(false);
    BtnKatara.setDisable(false);

    //Define a imagem dos botoes de pausa (icones de play/pause) como uma imagem de pausa.
    Image imagem = new Image("img/iconPause.png");
    imgBtnPauseAang.setImage(imagem);
    imgBtnPauseSokka.setImage(imagem);
    imgBtnPauseZuko.setImage(imagem);
    imgBtnPauseToph.setImage(imagem);
    imgBtnPauseKatara.setImage(imagem);

    //Configura a exibicao inicial dos elementos da interface para representar as "nacoes" pensando e prontas para comer.
    for(int i = 0; i < 5; i++){
      setPensamento(i, true);
      setEsperando(i, false);
      setComida(i, false);
      setHashi(i, true);
      setUsandoHashis(i, false);
    }
  }

  //---------------------------------- METODOS PARA SETAR PENSAMENTO, COMIDA, ESPERA, HASHIS ----------------------------------//
/* ***************************************************************
* Metodo: setPensamento.
* Funcao: Definir a visibilidade de uma imagem que representa o estado de pensamento de uma "nacao".
* Parametros: int i - identificar a nacao, boolean valor - determinar se a imagem de pensamento da "nacao" deve ser visivel ou invisivel.
* Retorno: nenhum (void).
*************************************************************** */
  public void setPensamento(int i, boolean valor) {
    switch (i) {
      case 0: {
        imagemPensamentoAang.setVisible(valor);
        break;
      }
      case 1: {
        imagemPensamentoSokka.setVisible(valor);
        break;
      }
      case 2: {
        imagemPensamentoZuko.setVisible(valor);
        break;
      }
      case 3: {
        imagemPensamentoToph.setVisible(valor);
        break;
      }
      case 4: {
        imagemPensamentoKatara.setVisible(valor);
        break;
      }
    }
  }

/* ***************************************************************
* Metodo: setComida.
* Funcao: Definir a visibilidade de uma imagem que representa o estado de comendo de uma "nacao".
* Parametros: int i - identificar a nacao, boolean valor - determinar se a imagem de comendo da "nacao" deve ser visivel ou invisivel.
* Retorno: nenhum (void).
*************************************************************** */
  public void setComida(int i, boolean valor) {
    switch (i) {
      case 0: {
        imagemComidaAang.setVisible(valor);
        break;
      }
      case 1: {
        imagemComidaSokka.setVisible(valor);
        break;
      }
      case 2: {
        imagemComidaZuko.setVisible(valor);
        break;
      }
      case 3: {
        imagemComidaToph.setVisible(valor);
        break;
      }
      case 4: {
        imagemComidaKatara.setVisible(valor);
        break;
      }
    }
  }

/* ***************************************************************
* Metodo: setEsperando.
* Funcao: Definir a visibilidade de uma imagem que representa o estado de fome/esperando de uma "nacao".
* Parametros: int i - identificar a nacao, boolean valor - determinar se a imagem de fome/esperando da "nacao" deve ser visivel ou invisivel.
* Retorno: nenhum (void).
*************************************************************** */
  public void setEsperando(int i, boolean valor) {
    switch (i) {
      case 0: {
        aangEsperando.setVisible(valor);
        break;
      }
      case 1: {
        sokkaEsperando.setVisible(valor);
        break;
      }
      case 2: {
        zukoEsperando.setVisible(valor);
        break;
      }
      case 3: {
        tophEsperando.setVisible(valor);
        break;
      }
      case 4: {
        kataraEsperando.setVisible(valor);
        break;
      }
    }
  }

/* ***************************************************************
* Metodo: setHashi.
* Funcao: Definir a visibilidade de uma imagem que representa o hashi.
* Parametros: int i - identificar a nacao, boolean valor - determinar se a imagem de hashi da "nacao" deve ser visivel ou invisivel.
* Retorno: nenhum (void).
*************************************************************** */
  public void setHashi(int i, boolean valor) {
    switch (i) {
      case 0: {
        hashi0.setVisible(valor);
        hashi1.setVisible(valor);
        break;
      }
      case 1: {
        hashi1.setVisible(valor);
        hashi2.setVisible(valor);
        break;
      }
      case 2: {
        hashi2.setVisible(valor);
        hashi3.setVisible(valor);
        break;
      }
      case 3: {
        hashi3.setVisible(valor);
        hashi4.setVisible(valor);
        break;
      }
      case 4: {
        hashi4.setVisible(valor);
        hashi0.setVisible(valor);
        break;
      }
    }
  }

/* ***************************************************************
* Metodo: setUsandoHashi.
* Funcao: Definir a visibilidade de uma imagem que representa o uso dos hashis.
* Parametros: int i - identificar a nacao, boolean valor - determinar se a imagem de uso dos hashis da "nacao" deve ser visivel ou invisivel.
* Retorno: nenhum (void).
*************************************************************** */
  public void setUsandoHashis(int i, boolean valor){
    switch (i) {
      case 0: {
        imagemHashiUsandoAang.setVisible(valor);
        break;
      }
      case 1: {
        imagemHashiUsandoSokka.setVisible(valor);
        break;
      }
      case 2: {
        imagemHashiUsandoZuko.setVisible(valor);
        break;
      }
      case 3: {
        imagemHashiUsandoToph.setVisible(valor);
        break;
      }
      case 4: {
        imagemHashiUsandoKatara.setVisible(valor);
        break;
      }
    }
  }

  //---------------------------------- ATRIBUTOS E METODOS REFERENTES A PAUSA DAS THREADS ----------------------------------//
  @FXML
  private int contAang = 0;// Contador relacionado com a mudanca de imagem do botao play/pause do Aang.

/* ***************************************************************
* Metodo: pausarAang.
* Funcao: Pausar ou dar play na "nacao" do Aang, atualizando a imagem do botao.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void pausarAang(ActionEvent event) {
    nacao[0].Pausar();// Pausa ou retoma a execucao da thread na simulacao.
    imgBtnPauseAang.setImage(mudarBotaoImagem(contAang));// Atualiza a imagem do botao.
    contAang++;// Incrementa o contador para alternar entre play e pause.
    if (contAang % 2 != 0) {
      System.out.println("Aang esta pausado");
    }
  }


  @FXML
  private int contSokka = 0;// Contador relacionado com a mudanca de imagem do botao play/pause do Sokka.

/* ***************************************************************
* Metodo: pausarSokka.
* Funcao: Pausar ou dar play na "nacao" do Sokka, atualizando a imagem do botao.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void pausarSokka(ActionEvent event) {
    nacao[1].Pausar();// Pausa ou retoma a execucao da thread na simulacao.
    imgBtnPauseSokka.setImage(mudarBotaoImagem(contSokka));// Atualiza a imagem do botao.
    contSokka++;// Incrementa o contador para alternar entre play e pause.
    if (contSokka % 2 != 0) {
      System.out.println("Sokka esta pausado");
    }
  }

  @FXML
  private int contZuko = 0;// Contador relacionado com a mudanca de imagem do botao play/pause do Zuko.

/* ***************************************************************
* Metodo: pausarZuko.
* Funcao: Pausar ou dar play na "nacao" do Zuko, atualizando a imagem do botao.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void pausarZuko(ActionEvent event) {
    nacao[2].Pausar();// Pausa ou retoma a execucao da thread na simulacao.
    imgBtnPauseZuko.setImage(mudarBotaoImagem(contZuko));// Atualiza a imagem do botao.
    contZuko++;// Incrementa o contador para alternar entre play e pause.
    if (contZuko % 2 != 0) {
      System.out.println("Zuko esta pausado");
    }
  }

  @FXML
  private int contToph = 0;// Contador relacionado com a mudanca de imagem do botao play/pause da Toph.

/* ***************************************************************
* Metodo: pausarToph.
* Funcao: Pausar ou dar play na "nacao" da Toph, atualizando a imagem do botao.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void pausarToph(ActionEvent event) {
    nacao[3].Pausar();// Pausa ou retoma a execucao da thread na simulacao.
    imgBtnPauseToph.setImage(mudarBotaoImagem(contToph));// Atualiza a imagem do botao.
    contToph++;// Incrementa o contador para alternar entre play e pause.
    if (contToph % 2 != 0) {
      System.out.println("Toph esta pausada");
    }
  }

  @FXML
  private int contKatara = 0;// Contador relacionado com a mudanca de imagem do botao play/pause da Katara.

/* ***************************************************************
* Metodo: pausarKatara.
* Funcao: Pausar ou dar play na "nacao" da Katara, atualizando a imagem do botao.
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void pausarKatara(ActionEvent event) {
    nacao[4].Pausar();// Pausa ou retoma a execucao da thread na simulacao.
    imgBtnPauseKatara.setImage(mudarBotaoImagem(contKatara));// Atualiza a imagem do botao.
    contKatara++;// Incrementa o contador para alternar entre play e pause.
    if (contKatara % 2 != 0) {
      System.out.println("Katara esta pausada");
    }
  }

/* ***************************************************************
* Metodo: mudarBotaoImagem.
* Funcao: Muda a imagem dos botoes de Play/Pause dependendo do cont de cada "nacao".
* Parametros: int cont - Um contador associado a uma "nacao". 
* Retorno: Image imagem.
*************************************************************** */
  public Image mudarBotaoImagem(int cont) {
    Image imagem;
    // Verifica se o contador e par ou impar.
    if (cont % 2 == 0) {
      imagem = new Image("img/iconPlay.png");// Se o contador cont for par, ele atribui a imagem "img/iconPlay.png" a variavel imagem.
    } else {
      imagem = new Image("img/iconPause.png");// Se o contador cont for impar, ele atribui a imagem "img/iconPause.png" a variavel imagem.
    }
    return imagem;// Retorna a imagem correspondente com base no valor do contador.
  }

  //---------------------------------- METODO PARA RESETAR ----------------------------------//
/* ***************************************************************
* Metodo: Resetar.
* Funcao: Redefinir e reiniciar a simulacao do "Jantar das Nacoes" para seu estado inicial. 
* Parametros: event (O evento de clique do mouse que acionou o metodo).
* Retorno: nenhum (void).
*************************************************************** */
  @FXML
  void Resetar(ActionEvent event) {
    // Zera os contadores que controlam a alternancia de imagens dos botoes de "play" e "pause" para as diferentes "nacoes".
    contAang = 0;
    contSokka = 0;
    contZuko = 0;
    contToph = 0;
    contKatara = 0;

    for (Nacao nacao : nacao) {
      nacao.pararNacao();// Interromper "nacao".
      nacao.recomecarNacao();// Reiniciar "nacao".
      if(nacao.flag == true){
        nacao.Pausar();//Para tornar a flag = false("nacao" despausada).
      }
    }


    // Indicando que a simulacao foi reiniciada.
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("!SIMULACAO REINICIADA!");
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();

    // Iniciar a simulacao novamente, simulando o processo de reinicializacao.
    CliqueEmIniciar(event);
  }
}