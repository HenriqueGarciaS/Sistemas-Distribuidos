package Servidor;

import mensagem.Mensagem;

import java.io.*;
import java.net.Socket;

public class Servidor {
    private int porta;
    private Socket conexao;
    private Mensagem mensagem;

    public static void main(String [] args) throws Exception{
     try{
         Servidor servidor;
         Socket conexaoDir = new Socket("127.0.0.1", 32121);
         BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
         ObjectOutputStream transmissor = new ObjectOutputStream(conexaoDir.getOutputStream());
         ObjectInputStream  receptor = new ObjectInputStream(conexaoDir.getInputStream());
         System.out.println("Digite a porta que será usado por esse servidor");
         int porta=Integer.parseInt(teclado.readLine());
         servidor= new Servidor(porta);
         System.out.println("mandando mesagem de cadastro ao diretorio");
         transmissor.writeObject(servidor.criarMensagemdeCadastro());




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
