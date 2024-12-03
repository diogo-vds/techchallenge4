package com.fiap.crudclientes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    public ClienteDTO() {}

    public ClienteDTO(Integer id, String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }
}
