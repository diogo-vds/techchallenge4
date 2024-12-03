package com.fiap.crudclientes.service;

import com.fiap.crudclientes.exception.ClienteException;
import com.fiap.crudclientes.util.ClienteConverter;
import com.fiap.crudclientes.dto.ClienteDTO;
import com.fiap.crudclientes.entity.ClienteEntity;
import com.fiap.crudclientes.repository.ClienteRepository;
import com.fiap.crudclientes.util.ValidadorCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    public List<ClienteDTO> listar() {
        return ClienteConverter.toListDto(repository.findAll());
    }

    public ClienteDTO buscarPorId(Integer id) {
        return ClienteConverter.entityToDTO(repository.findById(id).orElse(null));
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        return ClienteConverter.entityToDTO(repository.findByCpf(cpf));
    }

    public ClienteDTO buscarPorEmail(String cpf) {
        return ClienteConverter.entityToDTO(repository.findByCpf(cpf));
    }

    @Transactional
    public void cadastrar(ClienteDTO cliente) throws ClienteException {
        this.validarDadosCliente(cliente);
        this.buscarPorCpf(cliente.getCpf());
        repository.save(ClienteConverter.dtoToEntity(cliente));
    }

    public void validarDadosCliente(ClienteDTO cliente) throws ClienteException {
        if (cliente == null) {
            throw new ClienteException("Preencha os dados do cliente");
        }
        if(!ValidadorCPF.isValidCPF(cliente.getCpf())){
            throw new ClienteException("CPF inválido");
        }
        ClienteDTO cadastro = this.buscarPorCpf(cliente.getCpf());
        if(cadastro != null && cliente.getCpf().equals(cadastro.getCpf())){
            throw new ClienteException("Já existe um cadastro com este CPF");
        }
        cadastro = this.buscarPorEmail(cliente.getEmail());
        if(cadastro != null && cliente.getEmail().equals(cadastro.getEmail())){
            throw new ClienteException("Já existe um cadastro com este e-mail");
        }
    }

    @Transactional
    public void alterar(ClienteDTO cliente) {
        Optional optional = repository.findById(cliente.getId());
        if(optional.isPresent()){
            ClienteEntity entity = (ClienteEntity) optional.get();
            entity.setCpf(cliente.getCpf());
            entity.setNome(cliente.getNome());
            entity.setEmail(cliente.getEmail());
            entity.setTelefone(cliente.getTelefone());
            entity.setEndereco(cliente.getEndereco());
            repository.saveAndFlush(entity);
        }
    }

    public void excluir(Integer id) {
        repository.deleteById(id);
    }

}
