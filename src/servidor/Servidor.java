package servidor;

import mensagem.Mensagem;

public class Servidor {
    private int porta;
    private Mensagem mensagem;


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

