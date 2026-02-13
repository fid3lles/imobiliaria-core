package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import java.util.List;

public record BuscaPropriedadeFiltro (
        Long id,
        String condominio,
        String bairro,
        String cidade,
        Boolean aceitaPermuta,
        String tipo,
        Modalidade modalidade,
        Double areaPrincipalMin,
        Double areaPrincipalMax,
        Double areaLoteMin,
        Double areaLoteMax,
        Integer qtdQuartos,
        Integer qtdBanheiros,
        Integer qtdSuites,
        Integer qtdVagas,
        List<String> caractInternasContem,
        List<String> caractExternasContem,
        Double valorImovelMin,
        Double valorImovelMax,
        Double valorCondominioMin,
        Double valorCondominioMax,
        Double valorIptuMin,
        Double valorIptuMax
) {}
