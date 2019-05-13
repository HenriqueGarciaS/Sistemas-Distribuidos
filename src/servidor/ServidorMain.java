package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mensagem.Mensagem;

public class ServidorMain {
	
	 public static void main(String [] args) throws Exception{
	  try{
	      Servidor servidor;
	      BufferedReader teclado = new BufferedReader( new InputStreamReader(System.in));
	      String porta = null;
	      ArrayList<String> log = null;
	      do{
	          System.out.println("digite a porta que será usado pelo servidor no máximo 5 números");
	          porta = teclado.readLine();
	          int portaDoservidor = Integer.parseInt(porta);
	          servidor = new Servidor(portaDoservidor);
          }while(porta.length()<5 && porta.length()>6);
	      Socket conexaoAoDiretorio = new Socket("127.0.0.1",32221);
	      ObjectOutputStream transmissor = new ObjectOutputStream(conexaoAoDiretorio.getOutputStream());
	      System.out.println("Enviando mensagem de cadastrado ao diretorio");
	      transmissor.writeObject(servidor.criarMensagemdeCadastro());
	      transmissor.flush();
	      ServerSocket requisisaoDodiretorio = new ServerSocket(servidor.getPorta());
	      System.out.println("esperando requisições");
	      while(true){
	      	Socket respostaParacliente = requisisaoDodiretorio.accept();
	      	ObjectInputStream receptor_requisisao = new ObjectInputStream(respostaParacliente.getInputStream());
	      	Mensagem requisisao  = (Mensagem) receptor_requisisao.readObject();
	      	switch (requisisao.getTipoDaMensagem())
			{
				case 1: {
					VerificarTipoDeDado(requisisao,servidor);
					break;
				}
					case 2: {
						escritaDedado(requisisao,servidor);
						break;
					}
			}
	      	escreveLog(log,servidor,requisisao);
	      	
		  }
	      
      }
	  catch(Exception erro)
         {
             System.err.println("erro ao iniciar");
         }
}

public static void VerificarTipoDeDado(Mensagem mensagem, Servidor servidor) throws Exception
{
	switch (mensagem.getMensagem())
	{
		case "LETRA": {
			Socket conexaoAocliente = new Socket("127.0.0.1", mensagem.getPorta());
			Mensagem envio = new Mensagem(0, servidor.getLetra(), 32221);
			ObjectOutputStream transmissor_resposta = new ObjectOutputStream(conexaoAocliente.getOutputStream());
			transmissor_resposta.writeObject(envio);
			transmissor_resposta.flush();
			conexaoAocliente.close();
			break;
		}
		case "NUMERO": {
			Socket conexaoAocliente = new Socket("127.0.0.1", mensagem.getPorta());
			Mensagem envio = new Mensagem(0,servidor.getNumero(),32221);
			ObjectOutputStream transmissor_resposta = new ObjectOutputStream(conexaoAocliente.getOutputStream());
			transmissor_resposta.writeObject(envio);
			transmissor_resposta.flush();
			conexaoAocliente.close();
			break;
		}
	}
}


public static void escritaDedado(Mensagem mensagem, Servidor servidor) throws Exception
{
	switch(mensagem.getMensagem())
	{
		case "LETRA":
		{
			Socket conexaoAocliente = new Socket("127.0.0.1",mensagem.getPorta());
			Mensagem envio = new Mensagem(0,"valor do atributo:"+servidor.getLetra(), mensagem.getPorta());
			ObjectOutputStream transmissorAocliente = new ObjectOutputStream(conexaoAocliente.getOutputStream());
			transmissorAocliente.writeObject(envio);
			transmissorAocliente.flush();
			ObjectInputStream receptorDocliente = new ObjectInputStream(conexaoAocliente.getInputStream());
			Mensagem variavel = (Mensagem) receptorDocliente.readObject();
			servidor.escreverNaletra(variavel);
            Mensagem resposta = new Mensagem(0,"valor mudado",mensagem.getPorta());
			transmissorAocliente.writeObject(resposta);
			transmissorAocliente.flush();
			conexaoAocliente.close();
			break;
		}
		case "NUMERO":
		{
			Socket conexaoAocliente = new Socket("127.0.0.1",mensagem.getPorta());
			Mensagem envio = new Mensagem(0,"valor do atributo:"+servidor.getNumero(), mensagem.getPorta());
			ObjectOutputStream transmissorAocliente = new ObjectOutputStream(conexaoAocliente.getOutputStream());
			transmissorAocliente.writeObject(envio);
			transmissorAocliente.flush();
			ObjectInputStream receptorDocliente = new ObjectInputStream(conexaoAocliente.getInputStream());
			Mensagem variavel = (Mensagem) receptorDocliente.readObject();
			servidor.escreverNonumero(variavel);
			Mensagem resposta = new Mensagem(0,"valor mudado",mensagem.getPorta());
			transmissorAocliente.writeObject(resposta);
			transmissorAocliente.flush();
			conexaoAocliente.close();
			break;
		}
	}
}

public static void escreveLog(ArrayList<String> log, Servidor servidor, Mensagem mensagem)
{
  log = servidor.voltaLog(mensagem);
  for(int i = 0; i < log.size() ; i++ )
  System.out.println(log.get(i));
	  
  
	  
	
}
	}






