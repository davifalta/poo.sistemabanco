package models;

/**
 * Created by daviv on 19/06/2017.
 */
public class ClientePremium extends Cliente {


    public ClientePremium(String nome, String cpf, int year, int month, int day) {
        super(nome, cpf, year, month, day);
        this.conta = new ContaCorrente();
    }

    @Override
    public void setConta() {
        if(this.conta == null){
            conta = new ContaCorrente();
        }
    }

    @Override
    public double getLimite() {
        return 0;
    }
}