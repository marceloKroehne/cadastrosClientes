package com.totvs.cadastros.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Retorno {

    @Setter
    private int id;
    @Setter
    private Object dados;
    @Setter
    private String mensagem;
    private boolean houveErro;

    public Retorno(){
        this.id = 0;
        this.dados = "";
        this.mensagem="Sucesso!";
        this.houveErro = false;
    }

    public static Retorno novoRetornoErro(String mensagem){
        Retorno retorno = new Retorno();
        retorno.houveErro = true;
        retorno.mensagem = mensagem;

        return retorno;
    }
}
