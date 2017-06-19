package controllers;

import models.*;

/**
 * Created by daviv on 19/06/2017.
 */
public class OperacoesControllerCC extends OperacoesController{


    ContaCorrente c;

    public OperacoesControllerCC(ContaCorrente c){
        this.c = c;
    }



    public boolean pagarFatura(){
        c.calculaTotalFinanciado();
        if((c.getTotalFinanciado() != 0) && (c.getTotalFinanciado() < c.getSaldo())){
            double valor = (c.getSaldo() - c.getTotalFinanciado());
            c.setSaldo(valor);
            c.setTotalFinanciado(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean saque(double valor) {
        //cobrança de taxa por operação
        if(c.getContadorOperacoes() % 5 == 0){
            c.setSaldo(c.getSaldo() - 0.10);
        }
        //efetuando saque
        if(c.getSaldo() >= valor){
            double valorSaque = (c.getSaldo() - valor);
            c.setSaldo(valorSaque);
            c.setContadorOperacoes();
            return true;
        }else if(c.getSaldo() < valor){
            if(c.getLimite() > valor){
                double valorSaque = (c.getSaldo() - valor);
                c.setSaldo(valorSaque);
                double valorLimite = (c.getLimite() + c.getSaldo());
                c.setLimite(valorLimite);
                c.setContadorOperacoes();
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean transferencia(double valor, Conta x) {
       //cobrança de taxa por operação
        if(c.getContadorOperacoes() % 5 == 0){
            c.setSaldo(c.getSaldo() - 0.10);
            c.setSaldo(c.getSaldo() + valor);
        }
        //efetuando a transferencia
        if(c.getSaldo() >= valor){
            double valorTransferencia = (c.getSaldo() - valor);
            c.setSaldo(valorTransferencia);
            x.setSaldo(valor);
            c.setContadorOperacoes();
            return true;
        }else if(c.getSaldo() < valor){
            if(c.getLimite() > valor){
                double valorTransferencia = (c.getSaldo() - valor);
                c.setSaldo(valorTransferencia);
                double valorLimite = (c.getLimite() + c.getSaldo());
                c.setLimite(valorLimite);
                x.setSaldo(valor);
                c.setContadorOperacoes();
                return true;
            }
            return false;
        }

        c.setContadorOperacoes();
        c.setSaldo(c.getSaldo() + valor);
        return true;
    }

    @Override
    public boolean deposito(double valor) {
        if(valor > 0){
            c.setSaldo(valor);
            return true;
        }
        return false;
    }
}