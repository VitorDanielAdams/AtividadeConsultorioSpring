package br.com.consultorio.repository;

import br.com.consultorio.entity.Convenio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    @Modifying
    @Query("UPDATE Convenio convenio " +
            "SET convenio.ativo= :excluido " +
            "WHERE convenio.id = :convenio")
    public void disable(
            @Param("convenio") Long idConvenio,
            @Param("excluido") Boolean ativo);

    public Page<Convenio> findAllByNomeContaining(String name, Pageable pageable);
}
