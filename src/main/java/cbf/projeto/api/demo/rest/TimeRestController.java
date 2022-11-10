package cbf.projeto.api.demo.rest;

import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cbf.projeto.api.demo.dto.TimeDTO;
//import cbf.projeto.api.demo.entity.Time;
import cbf.projeto.api.demo.service.TimeServico;
import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value="/times")
public class TimeRestController {

    @Autowired // O uso do repositório diretamente no rest aqui é uma pratica ruim, uma boa pratica é utilizar um serviço, ja que ele serve para pegar o dado e jogar no banco, e então o serviço tem lógica
    private TimeServico timeservico;

    
    @GetMapping
    public ResponseEntity<List<TimeDTO>> getTime(){
        return ResponseEntity.ok().body(timeservico.listarTimes()); // É o método responsável por fazer o HTTP response das requisições feitas para a api. o metodo .ok() da instancia timeservico é pra devolver o HTTP 200(OK) e o body() devolver o conteúdo requisitado pela api
    }

    @ApiOperation(value = "Obtém os dados de um time")
    @GetMapping(value = "{id}")
    public ResponseEntity<TimeDTO> getTime(@PathVariable Integer id){
        return ResponseEntity.ok().body(timeservico.obterTime(id));
    }

    @PostMapping(value = "/cadastrar-time")
    public ResponseEntity<TimeDTO> cadastrarTime(@RequestBody TimeDTO time) throws Exception{
        return ResponseEntity.ok().body(timeservico.cadastrarTime(time));
    }
}
