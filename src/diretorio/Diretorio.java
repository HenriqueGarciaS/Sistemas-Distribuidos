package diretorio;

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
        try {
            ArrayList<Servidor> servidoresCadastrados = new ArrayList<>();
            int portaCliente;
            ServerSocket diretorio = new ServerSocket(32221);
            while (true) {
                System.out.println("diretorio está em execução");
                Socket conexao = diretorio.accept();
                ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
                ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
                System.out.println("sem deadlock, uhuu");
                Mensagem mensagem = (Mensagem) receptor.readObject();
                System.out.println(mensagem.getMensagem());
                switch (mensagem.getTipoDaMensagem()) {
                    case 0:
                        Servidor novoServidor = new Servidor(mensagem.getPorta());
                        servidoresCadastrados.add(novoServidor);
                        System.out.println("servidor de porta:" + mensagem.getPorta() + " cadastrado");
                        break;
                    case 1:
                        for (int i = 0; i < servidoresCadastrados.size(); i++) {
                            Servidor servidor = servidoresCadastrados.get(i);
                            Socket resposta = new Socket("127.0.0.1", servidor.getPorta());
                            portaCliente = mensagem.getPorta();
                            transmissor.writeObject(mensagem);
                            transmissor.flush();
                        }
                        break;
                    case 2:
                        portaCliente = mensagem.getPorta();
                        Socket respsota = new Socket("127.0.0.1", portaCliente);
                        transmissor.writeObject(mensagem);
                        transmissor.flush();
                }
            }

        } catch (Exception erro) {
            System.err.println("erro ao iniciar o diretorio");
        }

    }
}


