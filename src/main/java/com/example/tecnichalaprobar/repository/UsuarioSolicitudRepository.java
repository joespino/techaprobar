package com.example.tecnichalaprobar.repository;

import com.example.tecnichalaprobar.model.UsuarioSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioSolicitudRepository extends JpaRepository<UsuarioSolicitud, Integer>  {
    Optional<UsuarioSolicitud> findUsuarioSolicitudByUsuarioid(Integer usuarioid);
    List<UsuarioSolicitud> findUsuarioSolicitudBySolicitudid(Integer solicitudid);
}
