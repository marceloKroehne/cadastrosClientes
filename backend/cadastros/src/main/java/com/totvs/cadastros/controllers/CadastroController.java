package com.totvs.cadastros.controllers;

import com.totvs.cadastros.domains.Retorno;
import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.domains.requests.CadastroRequestDTO;
import com.totvs.cadastros.domains.requests.UpdateRequestDTO;
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

    @PostMapping("/deletarUsuario")
    public Retorno deletar(@RequestBody UUID id){

        try {
            cadastroService.deletarUsuario(id);
        }catch (Exception e){
            return Retorno.novoRetornoErro(e.getMessage());
        }

        return new Retorno();
    }

    @PostMapping("/editarUsuario")
    public Retorno editar(@RequestBody UpdateRequestDTO body){

        try {
          cadastroService.updateUsuario(body);
        }catch (Exception e){
            return Retorno.novoRetornoErro(e.getMessage());
        }

        return new Retorno();
    }

    @GetMapping("/listarUsuarios")
    public Retorno listar(@RequestParam(name="filtro") String filtro){

        Retorno retorno = new Retorno();

        retorno.setDados(cadastroService.listarUsuarios(filtro));

        return retorno;
    }
}
