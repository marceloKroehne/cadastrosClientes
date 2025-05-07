package com.totvs.cadastros.controllers;

import com.totvs.cadastros.domains.Retorno;
import com.totvs.cadastros.domains.requests.CadastroRequestDTO;
import com.totvs.cadastros.services.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @PostMapping("/novoUsuario")
    public Retorno cadastrar(@RequestBody CadastroRequestDTO body){

        Retorno retorno = new Retorno();
        try{
            cadastroService.cadastrarUsuario(body);
        }
        catch (Exception e){
            retorno = Retorno.novoRetornoErro(e.getMessage());
        }

        return retorno;

    }

    @DeleteMapping("/deletarUsuario/{id}")
    public Retorno deletar(@PathVariable UUID id) {
        try {
            cadastroService.deletarUsuario(id);
            return new Retorno();
        } catch (Exception e) {
            return Retorno.novoRetornoErro(e.getMessage());
        }
    }

    @PutMapping("/editarUsuario/{id}")
    public Retorno editar(@RequestBody CadastroRequestDTO body, @PathVariable UUID id) {
        try {
            cadastroService.updateUsuario(body, id);
            return new Retorno();
        } catch (Exception e) {
            return Retorno.novoRetornoErro(e.getMessage());
        }
    }

    @GetMapping("/listarUsuarios")
    public Retorno listar(@RequestParam(name="filtro") String filtro){

        Retorno retorno = new Retorno();

        retorno.setDados(cadastroService.listarUsuarios(filtro));

        return retorno;
    }

    @GetMapping("/buscarPorId")
    public Retorno listar(@RequestParam(name="id") UUID id){

        Retorno retorno = new Retorno();

        retorno.setDados(cadastroService.buscarPorId(id));

        return retorno;
    }
}
