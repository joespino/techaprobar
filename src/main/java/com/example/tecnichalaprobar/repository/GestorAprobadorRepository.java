package com.example.tecnichalaprobar.repository;

import com.example.tecnichalaprobar.model.GestorAprobador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorAprobadorRepository extends JpaRepository<GestorAprobador, Integer> {
    GestorAprobador findGestorAprobadorByUsuarioidAndSolicitudid(Integer usuarioid, Integer solicitudid);
}
