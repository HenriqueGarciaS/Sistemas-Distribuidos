package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import mensagem.Mensagem;

public class ServidorMain {
	
	 public static void main(String [] args) throws Exception{
	   try{
	   	  String respostaParaClienteLetra  = "A";
	   	  String respostaParaClienteNumero = "3";
          Servidor servidor;
	      Socket conexao = new Socket("127.0.0.1",32221);
	      ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
	      ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
	      BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	      String linha;
	      do{
	      	System.out.println("digite a porta que será usada por esses por esse servidor no maximo 5 números"); // até cinco numeros
	      	linha = teclado.readLine();
		  }while(linha.length()<5 && linha.length()<6);
	      int porta = Integer.parseInt(linha);
	      servidor = new Servidor(porta);
	      Mensagem mensagemAenviar = servidor.criarMensagemdeCadastro();
	      transmissor.writeObject(mensagemAenviar);
	      transmissor.flush();
	      System.out.println("Enviando a mensagem de cadastro ao diretorio");

	      while (true){
	      	System.out.println("Esperando requisões");
	      	ServerSocket resposta_do_diretorio = new ServerSocket(servidor.getPorta());
	      	Socket resposta = resposta_do_diretorio.accept();
	      	ObjectOutputStream transmissor_resposta = new ObjectOutputStream(resposta.getOutputStream());
	      	ObjectInputStream receptor_resposta = new ObjectInputStream(resposta.getInputStream());
	      	Mensagem mensagem_requisao = (Mensagem) receptor_resposta.readObject();
	      	if (mensagem_requisao.getMensagem().toUpperCase().equals("LETRA"))
			{
				Mensagem mensagem_resposta = new Mensagem(2,respostaParaClienteLetra,servidor.getPorta());
				transmissor.writeObject(mensagem_resposta);
				transmissor.flush();
			}
	      	else if(mensagem_requisao.getMensagem().toUpperCase().equals("NUMERO") || mensagem_requisao.getMensagem().toUpperCase().equals("NÚMERO"))
			{
				Mensagem mensagem_resposta = new Mensagem(2,respostaParaClienteNumero,servidor.getPorta());
				transmissor.writeObject(mensagem_resposta);
				transmissor.flush();
			}



	      }

	   }
	   catch(Exception erro)
	   {
	   	System.err.println("erro ao iniciar o servidor");
	   }
	 }
}


