package servidor;

import mensagem.Mensagem;

public class Servidor {
    private int porta;
    private Mensagem mensagem;
    private String DadoLetra = "A";
    private String DadoNumero = "3";
    private String replicaLetra = DadoLetra;
    private String replicaNumero = DadoNumero;


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

    public String getLetra()
    {
        return this.DadoLetra;
    }

    public String getNumero()
    {
        return this.DadoNumero;
    }

    public void escreverNonumero(Mensagem variavel){
        this.DadoNumero=variavel.getMensagem();
        this.replicaNumero=this.DadoNumero;
    }

    public void escreverNaletra(Mensagem variavel){
        this.DadoLetra=variavel.getMensagem();
        this.replicaLetra=this.DadoLetra;
    }

}

