package com.fiap.crudclientes.controller;

import com.fiap.crudclientes.dto.ClienteDTO;
import com.fiap.crudclientes.exception.ClienteException;
import com.fiap.crudclientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService service;

    @Operation(summary = "Lista os clientes cadastrados", description = "Retorna uma lista de clientes cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de clientes"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @GetMapping(path = "/listar", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClienteDTO>> listar(){
        List<ClienteDTO> clientes = service.listar();
        if(clientes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Consulta um cliente pelo Id", description = "Recebe um id e busca o cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Cliente nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @GetMapping(path = "/buscar-por-id/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id){
        ClienteDTO cliente = service.buscarPorId(id);
        if(cliente.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Consulta um cliente pelo cpf", description = "Recebe um cpf e busca o cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Cliente nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @GetMapping(path = "/buscar-por-cpf/{cpf}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity buscarPorCpf(@PathVariable String cpf){

        try {
            ClienteDTO cliente = service.buscarPorCpf(cpf);
            if(cliente.getId() == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ClienteException(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Cadastra cliente", description = "Recebe os dados de um novo cliente e cadastra na base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @PostMapping(path = "/cadastrar", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity cadastrar(@RequestBody ClienteDTO cliente){
        try {
            service.cadastrar(cliente);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ClienteException(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Altera Cadastro do cliente", description = "Recebe os dados de um cliente e altera na base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados alterados"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @PutMapping(path = "/alterar", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity alterar(@RequestBody ClienteDTO cliente){
        try {
            service.alterar(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ClienteException(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove Cadastro do cliente", description = "Recebe o id de um cliente e remove da base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados alterados"),
            @ApiResponse(responseCode = "400", description = "Erro na operacao")
    })
    @DeleteMapping(path = "/excluir/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity excluir(@PathVariable Integer id){
        try {
            service.excluir(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(new ClienteException(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    
}
