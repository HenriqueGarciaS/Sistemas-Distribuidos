package mensagem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Mensagem implements Serializable {

    private int tipoDaMensagem;
    private  String Mensagem;
    private int Porta;
    private long tempoDeinicio = System.nanoTime();


    public Mensagem(int tipoDaMensagem,String mensagem, int porta)
    {
        this.tipoDaMensagem=tipoDaMensagem;
        this.Mensagem=mensagem;
        this.Porta=porta;

    }

    public int getTipoDaMensagem()
    {
        return this.tipoDaMensagem;
    }

    public Long getTempoDeinicio()
    {
        return this.tempoDeinicio;
    }

    public String getMensagem()
    {
        return this.Mensagem;
    }


    public int getPorta()
    {
        return this.Porta;
    }

    public void setTipoDaMensagem(int tipo)
    {
        this.tipoDaMensagem=tipo;
    }

    public void setMensagem(String mensagem)
    {
        this.Mensagem=mensagem;
    }

    public void setPorta(int porta) {
        this.Porta=porta;
    }
}
