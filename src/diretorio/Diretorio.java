package diretorio;

import Servidor.Servidor;
import mensagem.Mensagem;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Diretorio {



        public static void main(String[] args) throws Exception {
        try {
            ArrayList<Servidor> servidoresCadastrados = new ArrayList<>();
            ServerSocket diretorio = new ServerSocket(32121);
            Socket conexao;
            ObjectOutputStream transmissor;
            ObjectInput receptor;

            while (true) {

                System.out.println("diretorio está em execução");
                conexao = diretorio.accept();
                receptor = new ObjectInputStream(conexao.getInputStream());
                System.out.println("alguem se conectou ao diretorio");
                Mensagem mensagem = (Mensagem) receptor.readObject();
                switch (mensagem.getTipoDaMensagem()) {
                    case 0:
                        Servidor novoServidor = new Servidor(mensagem.getPorta());
                        servidoresCadastrados.add(novoServidor);
                        break;

                    case 1:
                        for (Servidor servidor : servidoresCadastrados) {
                            Socket conexaoComServidor = new Socket("127.0.0.1", servidor.getPorta());
                            ObjectInputStream receptordeCliente = new ObjectInputStream(conexaoComServidor.getInputStream());
                            Mensagem mensagemDoCliente = (Mensagem) receptordeCliente.readObject();
                             transmissor = new ObjectOutputStream(conexaoComServidor.getOutputStream());
                            transmissor.writeObject(mensagemDoCliente);
                            break;
                        }
                }
            }
        } catch (Exception erro) {
            System.err.println("erro ao inicioar o diretorio");
        }


    }
    }


