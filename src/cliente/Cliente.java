package cliente;
import mensagem.Mensagem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;

public class Cliente {

    public static void main(String[] args) throws Exception{
       try{
           Socket conexao_ao_diretorio;
           ObjectOutputStream transmissor_ao_diretorio;
           BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
           String linha = "abbbb";
           int portaCliente = 32223;
           ServerSocket resposta_do_servidor = new ServerSocket(portaCliente);
           while(!linha.equals("FIM"))
           {
            System.out.println("Deseja fazer um resgate ou escrever? Digite fim para terminar");
            linha = teclado.readLine();
            linha = linha.toUpperCase();
            switch(linha)
            {
            case "RESGATE":
              {
                  System.out.println("digite o dado que deseja ser resgatado(Letra/Numero) ou digite fim para terminar a busca");
                  linha = teclado.readLine();
                  linha = linha.toUpperCase();
                  conexao_ao_diretorio = new Socket("127.0.0.1",32221);
                  transmissor_ao_diretorio = new ObjectOutputStream(conexao_ao_diretorio.getOutputStream());
                  Mensagem mensagem_envio = new Mensagem(1,linha,portaCliente);
                  transmissor_ao_diretorio.writeObject(mensagem_envio);
                  transmissor_ao_diretorio.flush();
                  Long tempoDechegada = System.nanoTime();
                  Socket resposta = resposta_do_servidor.accept();
                  ObjectInputStream receptor = new ObjectInputStream(resposta.getInputStream());
                  Mensagem mensagem_resposta = (Mensagem) receptor.readObject();
                  long tempoDamensagem = mensagem_resposta.getTempoDeinicio() - tempoDechegada;
                  double tempoFinal = tempoDamensagem /1000000000;
                  System.out.println("valor do atributo: "+mensagem_resposta.getMensagem()+" tempo da mensagem: "+tempoFinal);
                  conexao_ao_diretorio.close();
                  break;
               }
             case "ESCREVER":
                {
                    System.out.println("Em qual dado deseja escrever(Letra/Número)?");
                    linha = teclado.readLine().toUpperCase();
                    conexao_ao_diretorio = new Socket("127.0.0.1",32221);
                    transmissor_ao_diretorio = new ObjectOutputStream(conexao_ao_diretorio.getOutputStream());
                    Mensagem mensagem_envio = new Mensagem(2,linha,portaCliente);
                    transmissor_ao_diretorio.writeObject(mensagem_envio);
                    transmissor_ao_diretorio.flush();
                    Socket resposta = resposta_do_servidor.accept();
                    ObjectOutputStream escrever = new ObjectOutputStream(resposta.getOutputStream());
                    ObjectInputStream receptor = new ObjectInputStream(resposta.getInputStream());
                    Mensagem mensagem_resposta = (Mensagem) receptor.readObject();
                    System.out.println(mensagem_resposta);
                    System.out.println("digite o valor que será atribuido");
                    String valor = teclado.readLine();
                    Mensagem envio = new Mensagem(2,valor,portaCliente);
                    escrever.writeObject(envio);
                    escrever.flush();
                    conexao_ao_diretorio.close();
                    break;
                }
           }
           }
           resposta_do_servidor.close();
       }
       catch(Exception erro)
       {
           System.err.println("erro ao iniciar a comunicação ao diretorio");
       }
          }



        }








