package com.fiap.crudclientes.util;

import com.fiap.crudclientes.dto.ClienteDTO;
import com.fiap.crudclientes.entity.ClienteEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteConverter {

    private ClienteConverter(){}

    public static ClienteDTO entityToDTO(ClienteEntity entity){
        ClienteDTO dto = new ClienteDTO();
        if(Objects.isNull(entity)){
            return dto;
        }
        dto.setCpf(entity.getCpf());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setEndereco(entity.getEndereco());
        dto.setId(entity.getId());
        return dto;
    }

    public static ClienteEntity dtoToEntity(ClienteDTO dto){
        ClienteEntity entity = new ClienteEntity();
        entity.setCpf(dto.getCpf());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setId(dto.getId());
        return entity;
    }

    public static List<ClienteDTO> toListDto(List<ClienteEntity> entities){
        List<ClienteDTO> dtos = new ArrayList<>();
        entities.forEach(clienteEntity -> dtos.add(entityToDTO(clienteEntity)));
        return dtos;
    }

    public static List<ClienteEntity> dtoToEntities(List<ClienteDTO> dtos){
        List<ClienteEntity> entities = new ArrayList<>();
        dtos.forEach(clienteDTO -> entities.add(dtoToEntity(clienteDTO)));
        return entities;
    }

}
