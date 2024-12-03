package com.fiap.crudclientes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TbCliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDCLIENTE")
    private Integer id;
    @Column(name="NOME")
    private String nome;
    @Column(name="CPF")
    private String cpf;
    @Column(name="TELEFONE")
    private String telefone;
    @Column(name="EMAIL")
    private String email;
    @Column(name="ENDERECO")
    private String endereco;

}
