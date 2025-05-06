package com.totvs.cadastros.repositories;

import com.totvs.cadastros.domains.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
    Telefone findByTelefone(String telefone);

    @Transactional
    @Modifying
    @Query("DELETE FROM Telefone tel WHERE tel.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT tel FROM Telefone tel WHERE tel.usuario.id = :usuarioId")
    List<Telefone> buscarTelefonesPorUsuarioId(@Param("usuarioId")UUID usuarioId);
}
