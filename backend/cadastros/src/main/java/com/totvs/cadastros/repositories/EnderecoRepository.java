package com.totvs.cadastros.repositories;

import com.totvs.cadastros.domains.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Endereco end WHERE end.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT end FROM Endereco end WHERE end.usuario.id = :usuarioId")
    List<Endereco> buscarEnderecosPorUsuarioId(@Param("usuarioId") UUID usuarioId);
}
