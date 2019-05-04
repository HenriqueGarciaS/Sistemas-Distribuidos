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
           System.out.println("passou aqui 1");
           BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
           System.out.println("passou aqui 2");
           String linha = "abbbb";
           System.out.println("passou aqui 3");
           int portaCliente = 32223;
           System.out.println("passou aqui 4");
           ServerSocket resposta_do_servidor = new ServerSocket(portaCliente);
           System.out.println("passou aqui 5");
           while(!linha.equals("FIM"))
           {
             System.out.println("digite o dado que deseja ser regastado(Letra/Numero) ou digite fim para terminar a busca");

             linha = teclado.readLine();
             linha = linha.toUpperCase();
             conexao_ao_diretorio = new Socket("127.0.0.1",32221);
             transmissor_ao_diretorio = new ObjectOutputStream(conexao_ao_diretorio.getOutputStream());
             Mensagem mensagem_envio = new Mensagem(1,linha,portaCliente);
             transmissor_ao_diretorio.writeObject(mensagem_envio);
             transmissor_ao_diretorio.flush();
             Socket resposta = resposta_do_servidor.accept();
             ObjectInputStream receptor = new ObjectInputStream(resposta.getInputStream());
             Mensagem mensagem_resposta = (Mensagem) receptor.readObject();
             System.out.println(mensagem_resposta.getMensagem());
           }
       }
       catch(Exception erro)
       {
           System.err.println("erro ao iniciar a comunicação ao diretorio");
       }
    }

}
