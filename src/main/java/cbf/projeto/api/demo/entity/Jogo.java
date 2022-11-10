package cbf.projeto.api.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity //Informa para o spring boot que a classe é uma entidade
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer golstime1;
    private Integer golstime2;
    private Integer publicoPagante;
    private LocalDateTime dataJogo;
    private Integer rodada;
    private Boolean encerrado;
    // private Integer uuid;
    
    @ManyToOne //Informa para a aplicação que esss dois atributos servem para um relacionamento N->1
    // ao passar para a base de dados informações deles sendo chaves estrangeiras
    @JoinColumn(name="time1")// Como "Time" não é um tipo de dados em SQL, essa anotação transforma a classe desse atributo em um tipo legivel, como por exemplo INTEGER
    private Time time1;
    @ManyToOne //Informa para a aplicação que esss dois atributos servem para um relacionamento N->1
    // ao passar para a base de dados informações deles sendo chaves estrangeiras
    @JoinColumn(name="time2")
    private Time time2;
    
}
