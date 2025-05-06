package com.totvs.cadastros.biblioteca;

public class Biblioteca {

    public static boolean isCpfValido(String cpf){
        if(cpf.length() != 11)
            return false;

        int[] numeros1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] numeros2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int digito1 = 0;
        int digito2 = 0;

        for (int i = 0; i < numeros1.length; i++)
            digito1 += Character.getNumericValue(cpf.charAt(i)) * numeros1[i];

        for (int i = 0; i < numeros2.length; i++)
            digito2 += Character.getNumericValue(cpf.charAt(i)) * numeros2[i];

        int mod1 = digito1 % 11;
        int mod2 = digito2 % 11;

        digito1 = mod1 < 2 ? 0 : 11 - mod1;
        digito2 = mod2 < 2 ? 0 : 11 - mod2;

        return cpf.charAt(9) == digito1 + '0' && cpf.charAt(10) == digito2 + '0';

    }

    public static boolean StringMaiorIgual(String text, int tamanho){
        return text != null && text.length() >= tamanho;
    }

    public static String numeros(String s){
        StringBuilder r = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                r.append(s.charAt(i));
        }

        return r.toString();
    }

    public static boolean isTelefoneValido(String telefone){
        return StringMaiorIgual(numeros(telefone), 11);
    }

    public static boolean isCamposVazios(String... campos) {
        for(String campo : campos){
            if(isStringVazia(campo))
               return true;
        }

        return false;
    }

    public static boolean isStringVazia(String texto){
        return texto == null || texto.isEmpty();
    }
}
