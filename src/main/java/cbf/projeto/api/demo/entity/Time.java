package cbf.projeto.api.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity //Informa para o spring boot que a classe é uma entidade
public class Time{
    @Id // Informa que o atributo a seguir tambem vai ser uma chave primaria
    // @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera os valores de chave primaria para o atributo a seguir. Não funciona :)
    //Enquanto GeneratedValue gera os valores em si, o GenerationType se preocupa com os tipos de valores
    private Integer id;
    @Column(length = 20) //Define a quantidade de caracteres da coluna que vai ser criada a partir do atributo
    private String nome;
    @Column(length = 4)
    private String sigla;
    @Column(length = 2)
    private String uf;
    private String estadio;
}