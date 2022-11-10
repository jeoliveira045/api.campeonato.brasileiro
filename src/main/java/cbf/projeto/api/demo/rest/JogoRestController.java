package cbf.projeto.api.demo.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
/* import org.springframework.web.bind.annotation.RequestBody; */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cbf.projeto.api.demo.dto.ClassificacaoDTO;
import cbf.projeto.api.demo.dto.JogoDTO;
/* import cbf.projeto.api.demo.dto.JogoFinalizadoDTO;
import cbf.projeto.api.demo.entity.Jogo; */
import cbf.projeto.api.demo.service.JogoServico;

@RestController
@RequestMapping(value = "/jogos")
public class JogoRestController {

    @Autowired
    private JogoServico jogoService;

    @PostMapping(value = "/gerar-jogos") // uma boa pratica rest é deixar o nome das apis em minusculo
    public ResponseEntity<Void> gerarJogos(){
        jogoService.gerarJogos(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/obter-jogos")
    public ResponseEntity<List<JogoDTO>> obterJogos(){
        return ResponseEntity.ok().body(jogoService.listarJogos());
    }

    @PostMapping(value = "/finalizar/{id}")
    public ResponseEntity<JogoDTO> finalizarRandom(@PathVariable Integer id/* , @RequestBody JogoFinalizadoDTO jogoDTO */) throws Exception{
        jogoService.finalizarRandom(id /* jogoDTO */);
        return ResponseEntity.ok().build();
    }
 
    @GetMapping(value = "/classificacao")
    public ResponseEntity<ClassificacaoDTO> classificacao(){
        return ResponseEntity.ok().body(jogoService.obterClassificacao());
    }
    @GetMapping(value = "/jogo/{id}")
    public ResponseEntity<JogoDTO> obterJogo(@PathVariable Integer id){
        return ResponseEntity.ok().body(jogoService.obterJogo(id));
    }
}
