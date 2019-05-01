package Servidor;

import mensagem.Mensagem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private int porta;
    private Socket conexao;
    private Mensagem mensagem;

    public static void main(String [] args) throws Exception{
     try{
         BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
         System.out.println("Digite a porta que será usado por esse servidor");
         int porta=Integer.parseInt(teclado.readLine());
         System.out.println("porta atribuida teste1");
         Servidor servidor = new Servidor(porta);
         System.out.println("porta atribuida teste2");
         Socket conexaoDir = new Socket("127.0.0.1", 32121);
         System.out.println("passou aqui");
         ObjectOutputStream transmissor = new ObjectOutputStream(conexaoDir.getOutputStream());
         System.out.println("passou aqui");
         ObjectInputStream  receptor = new ObjectInputStream(conexaoDir.getInputStream());
         System.out.println("passou aqui");
         transmissor.writeObject(servidor.criarMensagemdeCadastro());
         System.out.println("passou aqui");
         System.out.println("mandando mesagem de cadastro ao diretorio");



         while(true){
             ServerSocket recebedor = new ServerSocket(servidor.getPorta());
             Socket conexao_recebedor = recebedor.accept();
             ObjectInputStream receptor2 = new ObjectInputStream(conexao_recebedor.getInputStream());
             ObjectOutputStream transmissor2 = new ObjectOutputStream(conexao_recebedor.getOutputStream());
             System.out.println("pronto para receber mensagens");
             Mensagem mensagem_recebida = (Mensagem) receptor2.readObject();
             System.out.println(mensagem_recebida.getMensagem());

         }
     }
     catch(Exception erro)
     {
         System.err.println("erro ao iniciar o servidor");
     }


    }


    public Servidor(int porta)
    {
        this.porta=porta;
    }

    public int getPorta() {
        return this.porta;
    }


    public Mensagem criarMensagemdeCadastro(){
        this.mensagem= new Mensagem(0,"Solicitando cadastro",this.porta);
        return this.mensagem;
    }

}
