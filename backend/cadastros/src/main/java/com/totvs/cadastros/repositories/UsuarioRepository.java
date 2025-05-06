package com.totvs.cadastros.repositories;

import com.totvs.cadastros.domains.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByCpf(String cpf);

    @Query("SELECT usu FROM Usuario usu WHERE usu.cpf LIKE CONCAT('%', :cpf, '%')")
    List<Usuario> findByCpfLike(@Param("cpf") String cpf);

    @Query("SELECT usu FROM Usuario usu WHERE LOWER(usu.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeLike(@Param("nome") String nome);
}
