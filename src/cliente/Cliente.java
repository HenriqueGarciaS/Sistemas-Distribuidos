package cliente;
import mensagem.Mensagem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws Exception{
   try{
       Socket conexao = new Socket("127.0.0.1", 32221);
       ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
       ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
       BufferedReader teclado = new BufferedReader( new InputStreamReader(System.in));
       String fim;
       do{
           System.out.println("digite o dado que deseja ser regastado ");
           String linha = teclado.readLine();
           Mensagem mensagem = new Mensagem(1,linha,32223);
           transmissor.writeObject(mensagem);
           transmissor.flush();
           mensagem = (Mensagem) receptor.readObject();
           System.out.println(mensagem.getMensagem());
           fim = mensagem.getMensagem();
       }while (fim.toUpperCase().equals("FIM"));
   }catch(Exception erro)
   {
       System.err.println("erro ao conectar ao diretorio");
   }


    }




}
