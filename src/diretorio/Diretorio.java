package diretorio;

import org.omg.CORBA.Object;
import servidor.Servidor;
import mensagem.Mensagem;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Diretorio {


    public static void main(String[] args) throws Exception {
      try{
          ArrayList<Servidor> servidoresCadastrados = new ArrayList<>();
          ServerSocket diretorio = new ServerSocket(32221);
          while(true){
              System.out.println("Diretorio está em execução");
              Socket conexao = diretorio.accept();
              ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
              Mensagem mensagem_recebida = (Mensagem) receptor.readObject();
              System.out.println("Mensagem recebida:");
              System.out.println(mensagem_recebida.getMensagem());
              switch (mensagem_recebida.getTipoDaMensagem()){
                  case 0:
                      System.out.println("cadastrando o servidor de porta:"+mensagem_recebida.getPorta());
                      Servidor servidor_para_cadastrar = new Servidor(mensagem_recebida.getPorta());
                      servidoresCadastrados.add(servidor_para_cadastrar);
                      break;
                  case 1:
                      System.out.println("Enviando ao todos os servidores para a recuperação de dados");
                      for( int i = 0; i < servidoresCadastrados.size(); i++)
                      {
                          Servidor servidorAenviar = servidoresCadastrados.get(i);
                          Socket requisisaoAServidor = new Socket("127.0.0.1", servidorAenviar.getPorta());
                          ObjectOutputStream transmissor = new ObjectOutputStream(requisisaoAServidor.getOutputStream());
                          transmissor.writeObject(mensagem_recebida);
                          transmissor.flush();
                      }
                      break;
                  case 2:
                      System.out.println("enviando a requisição de escrita para o servidor");
                      for(int i = 0 ; i < servidoresCadastrados.size(); i++)
                      {
                          Servidor servidorAenviar = servidoresCadastrados.get(i);
                          Socket requisisaoAServidor = new Socket("127.0.0.1", servidorAenviar.getPorta());
                          ObjectOutputStream transmissor = new ObjectOutputStream(requisisaoAServidor.getOutputStream());
                          transmissor.writeObject(mensagem_recebida);
                          transmissor.flush();
                      }
              }
              }
          }
      catch(Exception erro)
      {
          System.err.println("erro ao iniciar");
      }

      }


    }



