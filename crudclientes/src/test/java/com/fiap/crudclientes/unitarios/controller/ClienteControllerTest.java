package com.fiap.crudclientes.unitarios.controller;

import com.fiap.crudclientes.dto.ClienteDTO;
import com.fiap.crudclientes.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteService;

    @Test
    public void cadastrarCliente_DeveRetornarStatus201() throws Exception {
        List<ClienteDTO> list = clienteService.listar();
        list.forEach(clienteDTO -> clienteService.excluir(clienteDTO.getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"John Doe\", \"cpf\": \"200.272.100-90\", \"email\": \"johndoe@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void listarClientes_DeveRetornarListaDeClientes() throws Exception {
        List<ClienteDTO> list = clienteService.listar();
        if(Objects.isNull(list) || list.isEmpty()){
            clienteService.cadastrar(new ClienteDTO(1, "John Doe", "200.272.100-90", "123456789", "johndoe@example.com", "Rua 1"));
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("John Doe"));
    }

    @Test
    public void buscarPorId_DeveRetornarCliente() throws Exception {
        Integer id = 1;
        List<ClienteDTO> list = clienteService.listar();
        if(Objects.nonNull(list) && !list.isEmpty()){
            id = list.get(0).getId();
        }else{
            clienteService.cadastrar(new ClienteDTO(id, "John Doe", "200.272.100-90", "123456789", "johndoe@example.com", "Rua 1"));
        }
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/buscar-por-id/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void buscarPorCpf_DeveRetornarCliente() throws Exception {
        List<ClienteDTO> list = clienteService.listar();
        list.forEach(clienteDTO -> clienteService.excluir(clienteDTO.getId()));
        clienteService.cadastrar(new ClienteDTO(1, "John Doe", "200.272.100-90", "123456789", "johndoe@example.com", "Rua 1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/buscar-por-cpf/200.272.100-90")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void alterarCliente_DeveRetornarStatus200() throws Exception {
        List<ClienteDTO> list = clienteService.listar();
        list.forEach(clienteDTO -> clienteService.excluir(clienteDTO.getId()));
        clienteService.cadastrar(new ClienteDTO(1, "John Doe", "200.272.100-90", "123456789", "johndoe@example.com", "Rua 1"));

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/alterar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"nome\": \"John Doe2\", \"cpf\": \"200.272.100-90\", \"email\": \"johndoe@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void excluirCliente_DeveRetornarStatus204() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/excluir/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
