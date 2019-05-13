package servidor;

import java.util.ArrayList;

import mensagem.Mensagem;

public class Servidor {
    private int porta;
    private Mensagem mensagem;
    private String DadoLetra = "A";
    private String DadoNumero = "3";
    private String replicaLetra = DadoLetra;
    private String replicaNumero = DadoNumero;
    private ArrayList<String> log;


    public Servidor(int porta)
    {
        this.porta=porta;
        this.log = new ArrayList<>();
    }

    public int getPorta()
    {
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
        this.DadoNumero = variavel.getMensagem();
    }

    public void escreverNaletra(Mensagem variavel){
        this.DadoLetra = variavel.getMensagem();
        
    }
    
    public boolean controleDavariaveis()
    {
    	if(this.DadoLetra.equals(this.replicaLetra) && this.DadoNumero.equals(this.replicaNumero))
    	return true; 
    	else
    	{
    	this.replicaLetra  = this.DadoLetra;
    	this.replicaNumero = this.DadoNumero;
    	return false;
    	}
    }
    
    public ArrayList<String> voltaLog(Mensagem mensagem){
    	if(controleDavariaveis() == true)
    	{
    		String linha = "nenhuma mudança foi feita nas variaveis";
    		this.log.add(linha);
    		return this.log;
    	}
    	else
    	{
    		String linha = "houve mudança nas variaveis para:Letra "+this.DadoLetra+" Numero "+this.DadoNumero+" "+"pelo cliente:"+ mensagem.getPorta();
    	    this.log.add(linha);
    		return this.log;
    	}
    		
    	
    }
    

}

