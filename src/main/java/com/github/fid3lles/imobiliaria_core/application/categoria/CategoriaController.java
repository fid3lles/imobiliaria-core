package com.github.fid3lles.imobiliaria_core.application.categoria;

import com.github.fid3lles.imobiliaria_core.domain.propriedade.PropriedadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categoria")
@CrossOrigin
@RequiredArgsConstructor
public class CategoriaController {

    private final PropriedadeService propriedadeService;

    @GetMapping("/cidades")
    public List<String> buscarCidades() {
        return propriedadeService.getCidades();
    }

    @GetMapping("/bairros")
    public List<String> buscarBairrosUsandoCidade(@RequestParam("cidade") String cidade) {
        return propriedadeService.getBairrosPorCidade(cidade);
    }

    @GetMapping("/tipo-imovel")
    public List<String> buscarTodosTiposDeImovel() {
        return propriedadeService.getImovelTipos();
    }

    @GetMapping("/carac-internas")
    public List<String> buscarCaracteristicasInternas() {
        return propriedadeService.getCaracteristicasInternasTipos();
    }

    @GetMapping("/carac-externas")
    public List<String> buscarCaracteristicasExternas() {
        return propriedadeService.getCaracteristicasExternasTipos();
    }

}
