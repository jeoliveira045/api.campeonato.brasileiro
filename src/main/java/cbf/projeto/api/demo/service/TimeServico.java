package cbf.projeto.api.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import java.lang.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbf.projeto.api.demo.dto.TimeDTO;
import cbf.projeto.api.demo.entity.Time;
import cbf.projeto.api.demo.exception.TimeNullException;
import cbf.projeto.api.demo.repository.TimeRepository;

//Pesquisar a respeito da JpaRepository na documentação
@Service
public class TimeServico {

    @Autowired // O Autowired vai criar uma instância na variavel e vai passar pra ela todos os metodos que existem na interface
    private TimeRepository repository;

    public TimeDTO cadastrarTime(TimeDTO time) throws Exception{
        Time entity = toEntity(time);
        if (time.getNome() == null){
            throw new TimeNullException();
        }
        if (time.getId() == null){
            Integer newId  = Math.toIntExact(repository.count() + 1);
            entity.setId(newId);
            entity = repository.save(entity); // o método save do repositório adiciona o objeto no banco de dados na forma de uma linha
            return toDTO(entity);
        }
        else{
            throw new Exception("Time ja existe");   
        }
    }

    private Time toEntity(TimeDTO time){
        Time entity = new Time();
        entity.setId(time.getId());
        entity.setEstadio(time.getEstadio());
        entity.setSigla(time.getSigla());
        entity.setNome(time.getNome());
        entity.setUf(time.getUf());
        return entity;
         
    }

    public TimeDTO toDTO(Time entity) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(entity.getId());
        timeDTO.setEstadio(entity.getEstadio());
        timeDTO.setSigla(entity.getSigla());
        timeDTO.setNome(entity.getNome());
        timeDTO.setUf(entity.getUf());
        return timeDTO; 

    }

    public List<TimeDTO> listarTimes(){
        return repository.findAll().stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }


    public TimeDTO obterTime(Integer id) {
        return toDTO(repository.findById(id).get());
    }

    public List<Time> findAll() {
        return repository.findAll();
    }


}
