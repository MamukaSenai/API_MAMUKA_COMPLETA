package com.mamuka.apimamuka.repositories;

import com.mamuka.apimamuka.models.ProjetoModel;
import com.mamuka.apimamuka.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface ProjetoRepository extends JpaRepository<ProjetoModel, UUID> {

    List<ProjetoModel> findByUsuario(UsuarioModel gestor);

}

