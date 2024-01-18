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
package threads;

//Importacoes Necessarias.
import java.util.concurrent.Semaphore;// Importacao da classe Semaphore para lidar com semaforos.
import controller.PrincipalController;// Importando a classe de controlador PrincipalController, para interagir com a interface.
import javafx.scene.control.Slider;// Importacao para a classe 'Slider' do JavaFX, usada para criar controles deslizantes.
import javafx.application.Platform;// Importando a classe Platform para interacao com a plataforma JavaFX.

public class Nacao extends Thread {
  PrincipalController controlador;// Referencia ao controlador para interagir com a interface.
  public static int estado[] = new int[5];// Array para armazenar o estado de cada "nacao".
  public static Semaphore mutex = new Semaphore(1);// Semafaro para controlar o acesso a secoes criticas.
  public static Semaphore[] arraySemaphores = new Semaphore[5];// Semafaros para cada "nacao" controlarem o ato de comer.
  private final int id;// Identificador unico para cada "nacao".
  final int ESQ;// Indice do vizinho a esquerda.
  final int DIR;// Indice do vizinho a direita.
  int velocidadePensar = 5;// Velocidade padrao de pensamento.
  Slider sliderPensar;// Controle deslizante para ajustar a velocidade de pensamento na interface.
  int velocidadeComer = 5;// Velocidade padrao de alimentacao.
  Slider sliderComer;// Controle deslizante para ajustar a velocidade de alimentacao na interface.

  public boolean flag = false;// Sinalizador para pausar e retomar a execucao da thread.

  public final static int PENSANDO = 0;// Constante que representa o estado de pensamento.
  public final static int FOME = 1;// Constante que representa o estado de fome/esperando.
  public final static int COMENDO = 2;// Constante que representa o estado de alimentacao.

  private int condicao = 0;// Variavel para rastrear o estado ou condicao da "nacao."

/* ***************************************************************
* Metodo: Nacao.
* Funcao: Controlador.
* Parametros: int id, PrincipalController controlador, Slider sliderPensar, Slider sliderComer.
* Retorno: nenhum.
*************************************************************** */
  public Nacao(int id, PrincipalController controlador, Slider sliderPensar, Slider sliderComer) {
    this.id = id;
    this.controlador = controlador;
    this.ESQ = (id + 5 - 1) % 5;
    this.DIR = (id + 1) % 5;
    this.sliderPensar = sliderPensar;
    mudarVelocidadePensar(sliderPensar);
    this.sliderComer = sliderComer;
    mudarVelocidadeComer(sliderComer);
  }

/* ***************************************************************
* Metodo: run.
* Funcao: Define o comportamento da thread de cada "nacao" enquanto elas jantam.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  @Override
  public void run() {
    try{
      while (condicao >= 0) {
        while (flag) {
        	if(estado[id]==PENSANDO){
  	      	pensar();
        	}
        	if(estado[id]==COMENDO){
      			comer();
          }
        	if(estado[id]==FOME){

        	}
        }

        if (condicao == 0) {
          if(id == 0){
            System.out.println("Aang esta pensando");
          }
          if(id == 1){
            System.out.println("Sokka esta pensando"); 
          }
          if(id == 2){
            System.out.println("Zuko esta pensando");
          }
          if(id == 3){
            System.out.println("Toph esta pensando");
          }
          if(id == 4){
            System.out.println("Katara esta pensando");
          }
          pensar();
        }
        if (condicao == 1) {
          if(id == 0){
            System.out.println("Aang esta tentando pegar hashi");
          }
          if(id == 1){
            System.out.println("Sokka esta tentando pegar hashi"); 
          }
          if(id == 2){
            System.out.println("Zuko esta tentando pegar hashi");
          }
          if(id == 3){
            System.out.println("Toph esta tentando pegar hashi");
          }
          if(id == 4){
            System.out.println("Katara esta tentando pegar hashi");
          }
          pegarHashis();
        }
        if (condicao == 2) {
          if(id == 0){
            System.out.println("Aang pegou os hashis e esta comendo");
          }
          if(id == 1){
            System.out.println("Sokka pegou os hashis e esta comendo"); 
          }
          if(id == 2){
            System.out.println("Zuko pegou os hashis e esta comendo");
          }
          if(id == 3){
            System.out.println("Toph pegou os hashis e esta comendo");
          }
          if(id == 4){
            System.out.println("Katara pegou os hashis e esta comendo");
          }
          comer();
        }
        if (condicao == 3) {
          condicao = -1;
          if(id == 0){
            System.out.println("Aang colocou os hashis na mesa");
          }
          if(id == 1){
            System.out.println("Sokka colocou os hashis na mesa"); 
          }
          if(id == 2){
            System.out.println("Zuko colocou os hashis na mesa");
          }
          if(id == 3){
            System.out.println("Toph colocou os hashis na mesa");
          }
          if(id == 4){
            System.out.println("Katara colocou os hashis na mesa");
          }
          largarHashis();
        }
        condicao++;
      }
    }catch (ArrayIndexOutOfBoundsException e) {
      e.printStackTrace();
    }catch (NullPointerException e){
      e.printStackTrace();
    }
  }

  

  //---------------------------------- METODO RELACIONADO COM A PAUSA DA THREAD ----------------------------------//
/* ***************************************************************
* Metodo: Pausar.
* Funcao: Pausa ou retoma a execução da thread da "nacao".
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  public void Pausar() {
    this.flag = !flag;// Inverte o valor do sinalizador para pausar ou retomar a execucao.
  }

  //---------------------------------- PARTE DE IMPLEMENTACAO DO JANTAR DOS FILOSOFOS ----------------------------------//
/* ***************************************************************
* Metodo: Pensar.
* Funcao: Simula o estado de pensamento da "nacao" e atualiza a interface grafica.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  private void pensar() {
    Platform.runLater(() -> {
      mostrarPensamento(true);// Atualiza a interface para mostrar que a "nacao" esta pensando.
      mostrarEsperando(false);// Oculta a indicacao de que a "nacao" esta esperando.
      mostrarComida(false);// Oculta a indicacao de que a "nacao" esta comendo.
    });
    try {
      if (velocidadePensar == 0) {
        sleep(0);
      } else {
        sleep(20000 / velocidadePensar);// Simula o tempo de pensamento com base na velocidade definida.
      }
    } catch (InterruptedException e) {
    }
  }

/* ***************************************************************
* Metodo: PegarHashis.
* Funcao: Simula o ato de uma "nacao" tentar pegar os "hashis" e atualiza a interface grafica.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  public void pegarHashis() {
    Platform.runLater(() -> {
      mostrarPensamento(false);
    });
    try {
      mutex.acquire();// Tenta adquirir o semaforo para evitar concorrencia.
    } catch (InterruptedException e) {
    }
    estado[id] = FOME;// Define o estado da "nacao" como faminta.
    tentarPegarHashi(id);// Tenta pegar os "hashis".
    mutex.release();// Libera o semaforo.
    try {
      arraySemaphores[id].acquire();// Aguarda ate que seja possivel comer.
    } catch (InterruptedException e) {
    }
  }

/* ***************************************************************
* Metodo: tentarPegarHashi.
* Funcao: Simula a tentativa de uma "nacao" pegar os "hashis" e gerencia a competicao por eles.
* Parametros: int i - o indice da "nacao" que esta tentando pegar os "hashis".
* Retorno: nenhum.
*************************************************************** */
  public void tentarPegarHashi(int i) {
    int ESQ = (i + 5 - 1) % 5;// Atualiza vizinho da esquerda.
    int DIR = (i + 1) % 5;// Atualiza vizinho da direita.
    if (estado[i] == FOME
        && estado[ESQ] != COMENDO
        && estado[DIR] != COMENDO) {
      estado[i] = COMENDO;
      arraySemaphores[i].release();// Libera o semaforo para permitir que a "nacao" coma.
    } else {
      if (i == id) {
        Platform.runLater(() -> {
          mostrarPensamento(false);// Oculta a indicaçao de que a "nacao" esta pensando.
          mostrarEsperando(true);// Atualiza a interface para mostrar que a "nacao" esta esperando.
          mostrarComida(false);// Oculta a indicaco de que a "nacao" esta comendo.
        });
      }
    }
  }

/* ***************************************************************
* Metodo: Comer.
* Funcao: Simula o ato de uma "nacao" comendo e atualiza a interface grafica correspondente.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  private void comer() {
    Platform.runLater(() -> {
      mostrarPensamento(false);// Oculta a indicaçao de que a "nacao" esta pensando.
      mostrarEsperando(false);// Oculta a indicaçao de que a "nacao" esta esprando.
      mostrarComida(true);// Atualiza a interface para mostrar que a "nacao" esta comendo.
      mostrarHashi(false);//Oculta a indicacao de que os "hashis" estao disponiveis.
      mostrarUsandoHashi(true);// Exibe que a "nacao" esta usando os "hashis".
    });
    try {
      if (velocidadeComer == 0) {
        sleep(0);
      } else {
        sleep(20000 / velocidadeComer);// Simula o tempo de alimentacao com base na velocidade definida.
      }
    } catch (InterruptedException e) {
    }
  }

/* ***************************************************************
* Metodo: largarHashis.
* Funcao: Simula o ato de uma "nacao" largar os "hashis" apos terminar de comer e atualiza a interface grafica.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  public void largarHashis() {
    Platform.runLater(() -> {
      mostrarComida(false);// Oculta a indicaco de que a "nacao" esta comendo.
      mostrarHashi(true);// Exibe a disponibilidade dos "hashis".
      mostrarUsandoHashi(false);// Oculta a indicacao de que a "nacao" está usando os "hashis".
    });
    int ESQ = (id + 5 - 1) % 5;// Atualiza vizinho da esquerda.
    int DIR = (id + 1) % 5;// Atualiza vizinho da direita.
    try {
      mutex.acquire();// Tenta adquirir o semaforo para evitar concorrencia.
    } catch (InterruptedException e) {
    }
    estado[id] = PENSANDO;// Define o estado da "nacao" como pensando apos largar os "hashis".
    tentarPegarHashi(ESQ);// Tenta pegar o "hashi" a esquerda.
    tentarPegarHashi(DIR);// Tenta pegar o "hashi" a direita.
    mutex.release();// Libera o semaforo.
  }

  //---------------------------------- MOSTRANDO ELEMENTOS DA INTERFACE (PENSAMENTO, COMIDA, ESPERA, HASHIS) ----------------------------------//
/* ***************************************************************
* Metodo: mostrarPensamento.
* Funcao: Atualiza a interface grafica para indicar se a "nacao" esta pensando.
* Parametros: boolean valor - true para mostrar o pensamento, false para ocultar.
* Retorno: nenhum.
*************************************************************** */
  public void mostrarPensamento(boolean valor) {
    controlador.setPensamento(id, valor);// Atualiza a representacao grafica do pensamento da "nacao."
  }

/* ***************************************************************
* Metodo: mostrarComida.
* Funcao: Atualiza a interface grafica para indicar se a "nacao" esta comendo
* Parametros: boolean valor - true para mostrar a alimentacao, false para ocultar.
* Retorno: nenhum.
*************************************************************** */
  public void mostrarComida(boolean valor) {
    controlador.setComida(id, valor);// Atualiza a representacao grafica da alimentacao da "nacao."
  }

/* ***************************************************************
* Metodo: mostrarEsperando.
* Funcao: Atualiza a interface grafica para indicar se a "nacao" esta esperando
* Parametros: boolean valor - true para mostrar a espera, false para ocultar.
* Retorno: nenhum.
*************************************************************** */
  public void mostrarEsperando(boolean valor) {
    controlador.setEsperando(id, valor);// Atualiza a representacao grafica da espera da "nacao."
  }

/* ***************************************************************
* Metodo: mostrarHashi.
* Funcao: Atualiza a interface grafica para indicar se os "hashis" estao disponiveis.
* Parametros: boolean valor - true para mostrar a disponibilidade dos "hashis", false para ocultar.
* Retorno: nenhum.
*************************************************************** */
  public void mostrarHashi(boolean valor) {
    controlador.setHashi(id, valor);// Atualiza a representacao grafica da disponibilidade dos "hashis".
  }

/* ***************************************************************
* Metodo: mostrarUsandoHashi.
* Funcao: Atualiza a interface grafica para indicar se a "nacao" esta usando os "hashis".
* Parametros: boolean valor - true para mostrar o uso dos "hashis", false para ocultar.
* Retorno: nenhum.
*************************************************************** */
  public void mostrarUsandoHashi(boolean valor) {
    controlador.setUsandoHashis(id, valor);// Atualiza a representacao grafica do uso dos "hashis" pela "nacao".
  }

  //---------------------------------- MUDANDO VELOCIDADE DE PENSAR E COMER ----------------------------------//
/* ***************************************************************
* Metodo: mudarVelocidadePensar.
* Funcao: Atualiza a velocidade de pensamento da "nacao" com base no valor do controle deslizante (Slider).
* Parametros: Slider sliderPensar - o controle deslizante que controla a velocidade de pensamento.
* Retorno: nenhum.
*************************************************************** */
  public void mudarVelocidadePensar(Slider sliderPensar) {
    sliderPensar.valueProperty().addListener((observable, oldValue, newValue) -> {
      velocidadePensar = newValue.intValue();// Atualiza a velocidade de pensamento com base no valor do controle deslizante.
    });
  }

/* ***************************************************************
* Metodo: mudarVelocidadeComer.
* Funcao: Atualiza a velocidade da alimentacao da "nacao" com base no valor do controle deslizante (Slider).
* Parametros: Slider sliderComer - o controle deslizante que controla a velocidade da alimentacao.
* Retorno: nenhum.
*************************************************************** */
  public void mudarVelocidadeComer(Slider sliderComer) {
    sliderComer.valueProperty().addListener((observable, oldValue, newValue) -> {
      velocidadeComer = newValue.intValue();// Atualiza a velocidade da alimentacao com base no valor do controle deslizante.
    });
  }

  //---------------------------------- METODOS PARA RESETAR O JANTAR DAS NACOES ----------------------------------//
/* ***************************************************************
* Metodo: pararNacao.
* Funcao: Interrompe a execucao da "nacao" e reinicia suas configuracoes padrao, incluindo a velocidade de pensamento e alimentacao.
* Parametros: nenhum.
* Retorno: nenhum.
*************************************************************** */
  public void pararNacao() {
    condicao = -2;// Define a condicao como -2 para interromper a execucao da "nacao".
    velocidadePensar = 5;// Restaura a velocidade de pensamento para o valor padrao.
    velocidadeComer = 5;// Restaura a velocidade de alimentacao para o valor padrao.
  }

/* ***************************************************************
* Metodo: recomecarNacao.
* Funcao: Reinicia as configuracoes da "nacao" para os valores padrao de velocidade de pensamento e alimentacao.
* Parametros: Nenhum.
* Retorno: Nenhum.
*************************************************************** */
  public void recomecarNacao() {
    sliderPensar.setValue(1); // Define a velocidade de pensamento de volta ao valor padrao (1).
    sliderComer.setValue(1); // Define a velocidade de alimentacao de volta ao valor padrao (1).
  }
}