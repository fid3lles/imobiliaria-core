package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PropriedadeSpecification {

    public static Specification<Propriedade> comFiltro(BuscaPropriedadeFiltro f) {
        return (root, query, cb) -> {
            List<Predicate> p = new ArrayList<>();

            if (f == null) return cb.conjunction();

            // exatos
            if (f.id() != null) p.add(cb.equal(root.get("id"), f.id()));
            if (f.aceitaPermuta() != null) p.add(cb.equal(root.get("aceitaPermuta"), f.aceitaPermuta()));
            if (f.modalidade() != null) p.add(cb.equal(root.get("modalidade"), f.modalidade()));

            // string: LIKE ignore case
            likeIfPresent(p, cb, root.get("condominio"), f.condominio());
            likeIfPresent(p, cb, root.get("bairro"), f.bairro());
            likeIfPresent(p, cb, root.get("cidade"), f.cidade());
            likeIfPresent(p, cb, root.get("tipo"), f.tipo());

            // ranges (Double)
            betweenIfPresent(p, cb, root.get("areaPrincipal"), f.areaPrincipalMin(), f.areaPrincipalMax());
            betweenIfPresent(p, cb, root.get("areaLote"), f.areaLoteMin(), f.areaLoteMax());

            betweenIfPresent(p, cb, root.get("valorImovel"), f.valorImovelMin(), f.valorImovelMax());
            betweenIfPresent(p, cb, root.get("valorCondominio"), f.valorCondominioMin(), f.valorCondominioMax());
            betweenIfPresent(p, cb, root.get("valorIptu"), f.valorIptuMin(), f.valorIptuMax());

            // ints (se vier null, ignora)
            if (f.qtdQuartos() != null) p.add(cb.equal(root.get("qtdQuartos"), f.qtdQuartos()));
            if (f.qtdBanheiros() != null) p.add(cb.equal(root.get("qtdBanheiros"), f.qtdBanheiros()));
            if (f.qtdSuites() != null) p.add(cb.equal(root.get("qtdSuites"), f.qtdSuites()));
            if (f.qtdVagas() != null) p.add(cb.equal(root.get("qtdVagas"), f.qtdVagas()));

            // LISTAS (JSON TEXT) -> LIKE
            jsonArrayContainsAny(p, cb, root.get("caractInternas"), f.caractInternasContem());
            jsonArrayContainsAny(p, cb, root.get("caractExternas"), f.caractExternasContem());

            return cb.and(p.toArray(new Predicate[0]));
        };
    }

    private static void likeIfPresent(List<Predicate> p, jakarta.persistence.criteria.CriteriaBuilder cb,
                                      jakarta.persistence.criteria.Path<String> path, String value) {
        if (value == null || value.isBlank()) return;
        p.add(cb.like(cb.lower(path), "%" + value.toLowerCase().trim() + "%"));
    }

    private static void betweenIfPresent(List<Predicate> p, jakarta.persistence.criteria.CriteriaBuilder cb,
                                         jakarta.persistence.criteria.Path<Double> path,
                                         Double min, Double max) {
        if (min != null) p.add(cb.greaterThanOrEqualTo(path, min));
        if (max != null) p.add(cb.lessThanOrEqualTo(path, max));
    }

    private static void jsonArrayContainsAny(List<Predicate> p,
                                             jakarta.persistence.criteria.CriteriaBuilder cb,
                                             jakarta.persistence.criteria.Path<?> path,
                                             List<String> values) {
        if (values == null || values.isEmpty()) return;

        List<Predicate> ors = new ArrayList<>();
        for (String v : values) {
            if (v == null || v.isBlank()) continue;

            // procura como item JSON: ["X"] => "X" dentro do texto
            String pattern = "%\"" + v.trim() + "\"%";
            ors.add(cb.like(cb.lower(path.as(String.class)), pattern.toLowerCase()));
        }

        if (!ors.isEmpty()) {
            p.add(cb.or(ors.toArray(new Predicate[0])));
        }
    }
}
