package cbf.projeto.api.demo.dto;

import java.util.ArrayList;

import java.util.List;

import lombok.Data;

@Data
public class ClassificacaoDTO {

    private List<ClassificacaoTimeDTO> times = new ArrayList<>();
}
