package com.github.fid3lles.imobiliaria_core.application.busca;

import com.github.fid3lles.imobiliaria_core.domain.propriedade.BuscaPropriedadeFiltro;
import com.github.fid3lles.imobiliaria_core.domain.propriedade.Modalidade;
import com.github.fid3lles.imobiliaria_core.domain.propriedade.Propriedade;
import com.github.fid3lles.imobiliaria_core.domain.propriedade.PropriedadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/busca")
@CrossOrigin
@RequiredArgsConstructor
public class BuscaPropriedadeController {

    private final PropriedadeService propriedadeService;

    @GetMapping
    public Page<Propriedade> buscar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String condominio,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) Boolean aceitaPermuta,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Modalidade modalidade,

            @RequestParam(required = false) Double areaPrincipalMin,
            @RequestParam(required = false) Double areaPrincipalMax,
            @RequestParam(required = false) Double areaLoteMin,
            @RequestParam(required = false) Double areaLoteMax,

            @RequestParam(required = false) Integer qtdQuartos,
            @RequestParam(required = false) Integer qtdBanheiros,
            @RequestParam(required = false) Integer qtdSuites,
            @RequestParam(required = false) Integer qtdVagas,

            @RequestParam(required = false) List<String> caractInternasContem,
            @RequestParam(required = false) List<String> caractExternasContem,

            @RequestParam(required = false) Double valorImovelMin,
            @RequestParam(required = false) Double valorImovelMax,
            @RequestParam(required = false) Double valorCondominioMin,
            @RequestParam(required = false) Double valorCondominioMax,
            @RequestParam(required = false) Double valorIptuMin,
            @RequestParam(required = false) Double valorIptuMax,

            @RequestParam(required = false) Boolean destaque,

            @PageableDefault(size = 21) Pageable pageable
    ) {
        var filtro = new BuscaPropriedadeFiltro(
                id, condominio, bairro, cidade, aceitaPermuta, tipo, modalidade,
                areaPrincipalMin, areaPrincipalMax, areaLoteMin, areaLoteMax,
                qtdQuartos, qtdBanheiros, qtdSuites, qtdVagas,
                caractInternasContem, caractExternasContem,
                valorImovelMin, valorImovelMax,
                valorCondominioMin, valorCondominioMax,
                valorIptuMin, valorIptuMax
        );

        Page<Propriedade> buscar = propriedadeService.buscar(filtro, pageable);
        return buscar;
    }

    @GetMapping("/{id}")
    public Propriedade getPropriedade(@PathVariable Long id) {
        return propriedadeService.buscarPorID(id);
    }

}
