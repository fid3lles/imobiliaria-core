package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public Page<Propriedade> buscar(BuscaPropriedadeFiltro filtro, Pageable pageable) {
        Page<Propriedade> all = propriedadeRepository.findAll(PropriedadeSpecification.comFiltro(filtro), pageable);
        return all;
    }

    public Propriedade buscarPorID(Long id) {
        Optional<Propriedade> propriedadeOpt = propriedadeRepository.findById(id);
        return propriedadeOpt.orElse(null);
    }

    public List<String> getCidades() {
        return propriedadeRepository.findCidadesDistinctOrdenado();
    }

    public List<String> getBairrosPorCidade(String cidade) {
        return propriedadeRepository.findBairrosPorCidade(cidade);
    }

    public List<String> getImovelTipos() {
        return propriedadeRepository.findTipoDistinctOrdenado().stream().distinct().toList();
    }

    public List<String> getCaracteristicasInternasTipos() {
        List<String> caractInternas = extrairParaUmaListaDistinta(propriedadeRepository.findCaracteristicasInternas());
        return caractInternas;
    }

    public List<String> getCaracteristicasExternasTipos() {
        List<String> caractExternas = extrairParaUmaListaDistinta(propriedadeRepository.findCaracteristicasExternas());
        return caractExternas;
    }

    private static List<String> extrairParaUmaListaDistinta(List<List<String>> lista) {
        return lista.stream()
                        .flatMap(List::stream)
                        .distinct()
                        .toList();
    }
}
