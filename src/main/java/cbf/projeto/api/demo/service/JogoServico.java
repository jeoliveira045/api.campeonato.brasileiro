package cbf.projeto.api.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbf.projeto.api.demo.dto.ClassificacaoDTO;
import cbf.projeto.api.demo.dto.ClassificacaoTimeDTO;
import cbf.projeto.api.demo.dto.JogoDTO;
import cbf.projeto.api.demo.dto.JogoFinalizadoDTO;
import cbf.projeto.api.demo.entity.Jogo;
import cbf.projeto.api.demo.entity.Time;
import cbf.projeto.api.demo.repository.JogoRepository;
// import cbf.projeto.api.demo.repository.TimeRepository;


@Service
public class JogoServico {

    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    TimeServico timeServico;
    
    // @Autowired
    // Random numGen;// Uma boa pratica é o serviço não acessar um repositório que não é dele, então ele acessa ouitro serviço


    public void gerarJogos(LocalDateTime primeiraRodada){
        final List<Time> times = timeServico.findAll(); // Aqui é onde o serviço deveria fornecer os times do repositório para sortear as partidas da rodada
        List<Time> all1 = new ArrayList<>();
        List<Time> all2 = new ArrayList<>();
        all1.addAll(times);
        all2.addAll(times);

        jogoRepository.deleteAll();

        List<Jogo> jogos = new ArrayList<>();

        int t = times.size();
        int m = times.size()/2;
        LocalDateTime dataJogo = primeiraRodada;
        Integer rodada = 0;
        for (int i = 0; i < t-1; i++){
            rodada = i + 1;
            for (int j = 0; j < m; j++){
                // Teste par ajustar o mando de campo
                Time time1;
                Time time2;
                if(j%2 == 1 || i % 2 == 1 && j == 0){
                    time1 = times.get(t-j-1);
                    time2 = times.get(j);
                } else {
                    time1 = times.get(j);
                    time2 = times.get(t-j-1);       
                }
                if(time1 == null){
                    System.out.println("Time 1 is null");
                }
                jogos.add(gerarJogo(dataJogo, rodada, time1,time2));
                dataJogo = dataJogo.plusDays(7);
            }
            // Gira os times no sentido  horário mantendo o primeiro no lugar
            times.add(1, times.remove(times.size()-1));
        }

        jogos.forEach(jogo -> System.out.print(jogo));

        jogoRepository.saveAll(jogos);

        List<Jogo> jogos2 = new ArrayList<>();

        jogos.forEach(jogo -> {
            Time time1 = jogo.getTime2();
            Time time2 = jogo.getTime1();
            jogos2.add(gerarJogo(jogo.getDataJogo().plusDays(7 * jogos.size()), jogo.getRodada() + jogos.size(), time1, time2));
        });

        jogoRepository.saveAll(jogos2);

    }


    private Jogo gerarJogo(LocalDateTime dataJogo, Integer rodada, Time time1, Time time2) {
        Jogo jogo = new Jogo();
        jogo.setTime1(time1);
        jogo.setTime2(time2);
        jogo.setRodada(rodada);
        jogo.setDataJogo(dataJogo);
        jogo.setEncerrado(false);
        jogo.setGolstime1(0);
        jogo.setGolstime2(0);
        jogo.setPublicoPagante(0);
        return jogo;
    }

    private JogoDTO entityToDTO(Jogo entity){
        JogoDTO dto = new JogoDTO();
        dto.setId(entity.getId());
        dto.setDataJogo(entity.getDataJogo());
        dto.setEncerrado(entity.getEncerrado());
        dto.setGolstime1(entity.getGolstime1());
        dto.setGolstime2(entity.getGolstime2());
        dto.setPublicoPagante(entity.getPublicoPagante());
        dto.setRodada(entity.getRodada());
        dto.setTime1(timeServico.toDTO(entity.getTime1()));
        dto.setTime2(timeServico.toDTO(entity.getTime2()));
        return dto;
    }

/*     private Jogo dtoToEntity(JogoDTO jogo){
        Jogo entity = new Jogo();
        entity.setId(jogo.getId());
        entity.setDataJogo(jogo.getDataJogo());
        entity.setEncerrado(jogo.getEncerrado());
        entity.setGolstime1(jogo.getGolstime1());
    } */


    public List<JogoDTO> listarJogos() {
        return jogoRepository.findAll().stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }


    public JogoDTO finalizar(Integer id, JogoFinalizadoDTO jogoDTO) throws Exception {
        Optional<Jogo> optionalJogo = jogoRepository.findById(id);
        if(optionalJogo.isPresent()){
            final Jogo jogo = optionalJogo.get();
            jogo.setGolstime1(jogoDTO.getGolstime1());
            jogo.setGolstime2(jogoDTO.getGolstime2());
            jogo.setEncerrado(true);
            jogo.setPublicoPagante(jogoDTO.getPublicoPagante());
            return entityToDTO(jogoRepository.save(jogo));
        }
        else{
            throw new Exception("Jogo não existe");
        }
    } 

    public JogoDTO finalizarRandom(Integer id /* JogoFinalizadoDTO jogoDTO */) throws Exception {
        Random numGen = new Random();
        Optional<Jogo> optionalJogo = jogoRepository.findById(id);
        if(optionalJogo.isPresent()){
            final Jogo jogo = optionalJogo.get();
            jogo.setGolstime1(numGen.nextInt(5));
            jogo.setGolstime2(numGen.nextInt(5));
            jogo.setEncerrado(true);
            jogo.setPublicoPagante(numGen.nextInt(30000,43000));
            return entityToDTO(jogoRepository.save(jogo));
        }
        else{
            throw new Exception("Jogo não existe");
        }
    } 

    public ClassificacaoDTO obterClassificacao() {
        ClassificacaoDTO classificacaoDto = new ClassificacaoDTO();
        final List<Time> times = timeServico.findAll();

        times.forEach(time -> {
            final List<Jogo> jogosMandante = jogoRepository.findByTime1AndEncerrado(time, true);
            final List<Jogo> jogosVisitante = jogoRepository.findByTime2AndEncerrado(time, true);
            AtomicReference<Integer> vitorias = new AtomicReference<>(0); // Pesquisar mais a respeito sobre essa classe e por que ela é tilizada. Revisitar essa parte do projeto
            // pra fixar mais do por que dessa forma de escrever o codigo ter sido adotada
            AtomicReference<Integer> derrotas = new AtomicReference<>(0);
            AtomicReference<Integer> empates = new AtomicReference<>(0);
            AtomicReference<Integer> golsMarcados = new AtomicReference<>(0);
            AtomicReference<Integer> golsSofridos = new AtomicReference<>(0);

            jogosMandante.forEach(jogo -> {
                if (jogo.getGolstime1() > jogo.getGolstime2()){
                    vitorias.getAndSet(vitorias.get() + 1);
                }else if (jogo.getGolstime1() < jogo.getGolstime2()){
                    derrotas.getAndSet(derrotas.get() + 1);
                }else{
                    empates.getAndSet(empates.get() + 1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolstime1());
                golsSofridos.set(golsSofridos.get() + jogo.getGolstime2());
            });
            jogosVisitante.forEach(jogo -> {
                if (jogo.getGolstime2() > jogo.getGolstime1()){
                    vitorias.getAndSet(vitorias.get() + 1);
                }else if (jogo.getGolstime2() < jogo.getGolstime1()){
                    derrotas.getAndSet(derrotas.get() + 1);
                }else{
                    empates.getAndSet(empates.get() + 1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolstime2());
                golsSofridos.set(golsSofridos.get() + jogo.getGolstime1());
            });

            ClassificacaoTimeDTO classificacaoTimeDto = new ClassificacaoTimeDTO();

            classificacaoTimeDto.setIdTime(time.getId());
            classificacaoTimeDto.setTime(time.getNome());
            classificacaoTimeDto.setPontos((vitorias.get() * 3) + empates.get());
            classificacaoTimeDto.setDerrotas(derrotas.get());
            classificacaoTimeDto.setEmpates(empates.get());
            classificacaoTimeDto.setVitorias(vitorias.get());
            classificacaoTimeDto.setGolsMarcados(golsMarcados.get());
            classificacaoTimeDto.setGolsSofridos(golsSofridos.get());
            classificacaoTimeDto.setJogos(derrotas.get() + empates.get() + vitorias.get());
            classificacaoDto.getTimes().add(classificacaoTimeDto);
          
        });
        Collections.sort(classificacaoDto.getTimes(), Collections.reverseOrder());
        int posicao = 1;
        for(ClassificacaoTimeDTO time: classificacaoDto.getTimes()){
            time.setPosicao(posicao++);
        }


        return classificacaoDto;
    }


    public JogoDTO obterJogo(Integer id) {
        return entityToDTO(jogoRepository.findById(id).get());
    }
}
