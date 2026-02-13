package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import com.github.fid3lles.imobiliaria_core.domain.utils.StringListJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String condominio;
    private String bairro;
    private String cidade;
    private boolean aceitaPermuta;
    private String tipo;
    @Enumerated(EnumType.STRING)
    private Modalidade modalidade;
    private Double areaPrincipal;
    private Double areaLote;
    private int qtdQuartos;
    private int qtdBanheiros;
    private int qtdSuites;
    private int qtdVagas;
    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> caractInternas;
    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> caractExternas;
    private Double valorImovel;
    private Double valorCondominio;
    private Double valorIptu;
    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> midias;
    private boolean destaque;

}
