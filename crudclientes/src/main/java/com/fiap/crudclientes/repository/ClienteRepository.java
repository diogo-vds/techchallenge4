package com.fiap.crudclientes.repository;

import com.fiap.crudclientes.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<ClienteEntity, Integer> {

    ClienteEntity findByCpf(String cpf);
    ClienteEntity findByEmail(String email);

}
