package com.github.fid3lles.imobiliaria_core.domain.propriedade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long>, JpaSpecificationExecutor<Propriedade> {

    @Query("""
       select distinct p.cidade
       from Propriedade p
       where p.cidade is not null
       order by p.cidade
       """)
    List<String> findCidadesDistinctOrdenado();

    @Query("""
       select distinct p.tipo
       from Propriedade p
       where p.tipo is not null
       order by p.tipo
       """)
    List<String> findTipoDistinctOrdenado();

    @Query("""
        select distinct p.bairro
        from Propriedade p
        where p.cidade = :cidade
          and p.bairro is not null
        order by p.bairro
    """)
    List<String> findBairrosPorCidade(@Param("cidade") String cidade);

    @Query("select p.caractInternas from Propriedade p where p.caractInternas is not null")
    List<List<String>> findCaracteristicasInternas();

    @Query("select p.caractExternas from Propriedade p where p.caractExternas is not null")
    List<List<String>> findCaracteristicasExternas();


}
