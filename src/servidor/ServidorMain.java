package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import jdk.internal.util.xml.impl.Input;
import mensagem.Mensagem;

public class ServidorMain {
	
	 public static void main(String [] args) throws Exception{
	  try{
	      Servidor servidor;
	      BufferedReader teclado = new BufferedReader( new InputStreamReader(System.in));
	      String porta = null;
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
	      	Mensagem requisao  = (Mensagem) receptor_requisisao.readObject();
	      	switch (verificarTipoDeDado(requisao))
			{
				case 1: {
					Socket conexaoAocliente = new Socket("127.0.0.1", requisao.getPorta());
					Mensagem envio = new Mensagem(0, servidor.getLetra(), 32221);
					ObjectOutputStream transmissor_resposta = new ObjectOutputStream(conexaoAocliente.getOutputStream());
					transmissor_resposta.writeObject(envio);
					transmissor_resposta.flush();
					break;
				}
					case 2: {
						Socket conexaoAoCliente = new Socket("127.0.0.1", requisao.getPorta());
						Mensagem envio = new Mensagem(0,servidor.getNumero(),32221);
						ObjectOutputStream transmissor_resposta = new ObjectOutputStream(conexaoAoCliente.getOutputStream());
						transmissor_resposta.writeObject(envio);
						transmissor_resposta.flush();
						break;
					}
			}
		  }

      }
	  catch(Exception erro)
         {
             System.err.println("erro ao iniciar");
         }
}

public static int verificarTipoDeDado(Mensagem mensagem)
{
	switch (mensagem.getMensagem()){
		case "LETRA" :
			return 1;

		case "NUMERO" :
			return 2;


	}
return 0;
}

}


