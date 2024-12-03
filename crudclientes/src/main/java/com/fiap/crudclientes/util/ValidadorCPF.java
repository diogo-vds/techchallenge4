package com.fiap.crudclientes.util;

import java.util.Objects;

public class ValidadorCPF {

    public static boolean isValidCPF(String cpf) {
        if(Objects.nonNull(cpf) && !cpf.isEmpty()){
            cpf = cpf.replaceAll("\\.", "");
            cpf = cpf.replaceAll("-", "");
        }
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        try {
            int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * weights[i];
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit > 9) {
                firstDigit = 0;
            }
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * weights[i + 1];
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit > 9) {
                secondDigit = 0;
            }
            return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }
}
