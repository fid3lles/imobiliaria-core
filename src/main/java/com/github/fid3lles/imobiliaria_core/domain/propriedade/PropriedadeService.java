package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeService(PropriedadeRepository propriedadeRepository) {
        this.propriedadeRepository = propriedadeRepository;
    }

    @Value("${app.media.base-dir}")
    private String mediaBaseDir;

    @Value("${app.media.base-url}")
    private String mediaBaseUrl;

    private static final Set<String> EXT_OK = Set.of("jpg", "jpeg", "png", "webp");

    public Page<Propriedade> buscar(BuscaPropriedadeFiltro filtro, Pageable pageable) {
        Page<Propriedade> page = propriedadeRepository.findAll(
                PropriedadeSpecification.comFiltro(filtro),
                pageable
        );

        // embarca links no campo midias
        return page.map(this::embarcarMidias);
    }

    public Propriedade buscarPorID(Long id) {
        Optional<Propriedade> propriedadeOpt = propriedadeRepository.findById(id);
        return propriedadeOpt.map(this::embarcarMidias).orElse(null);
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
        return extrairParaUmaListaDistinta(propriedadeRepository.findCaracteristicasInternas());
    }

    public List<String> getCaracteristicasExternasTipos() {
        return extrairParaUmaListaDistinta(propriedadeRepository.findCaracteristicasExternas());
    }

    private Propriedade embarcarMidias(Propriedade p) {
        List<String> links = listarLinksImagens(p.getId());
        // sua entidade só tem getter; então você precisa de um setter OU um método no domínio.
        // Se você NÃO quiser setter público, crie um método package-private na entidade.
        p.setMidias(links);
        return p;
    }

    private List<String> listarLinksImagens(Long propriedadeId) {
        Path dir = Path.of(mediaBaseDir, String.valueOf(propriedadeId));

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            return List.of();
        }

        try (var stream = Files.list(dir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(this::isImagem)
                    .sorted()
                    .map(nomeArquivo -> mediaBaseUrl + "/" + propriedadeId + "/" + nomeArquivo)
                    .toList();
        } catch (IOException e) {
            return List.of();
        }
    }

    private boolean isImagem(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx < 0) return false;
        String ext = filename.substring(idx + 1).toLowerCase();
        return EXT_OK.contains(ext);
    }

    private static List<String> extrairParaUmaListaDistinta(List<List<String>> lista) {
        return lista.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
    }
}