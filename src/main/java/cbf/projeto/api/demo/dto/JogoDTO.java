package cbf.projeto.api.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JogoDTO {
    private Integer id;
    private Integer golstime1;
    private Integer golstime2;
    private Integer publicoPagante;
    private LocalDateTime dataJogo;
    private Integer rodada;
    private Boolean encerrado;
    private TimeDTO time1;
    private TimeDTO time2;
}
