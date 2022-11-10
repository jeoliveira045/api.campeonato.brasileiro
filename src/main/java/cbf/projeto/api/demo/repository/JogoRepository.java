package cbf.projeto.api.demo.repository;

import java.util.List;

//Uma vez que a modelagem conceitual e logica foi feita,
//  o arquivo spring com todas as dependencias foram adquiridos,
//   todos os pacotes para cada tipo de classe foram criados e 
//   as entidades foram devidamente configuradas para auxiliarem 
//   a construção do banco, chega a hora de criar os repositórios das entidades -> Seguir para services

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cbf.projeto.api.demo.entity.Jogo;
import cbf.projeto.api.demo.entity.Time;

@Repository //Informa para o spring boot que a classe é um repositório
public interface JogoRepository extends JpaRepository<Jogo, Integer>{

    List<Jogo> findByTime1AndEncerrado(Time time1, boolean encerrado);

    List<Jogo> findByTime2AndEncerrado(Time time2, boolean encerrado);
}
