package com.example.tecnichalaprobar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_usuariosolicitud")
public class UsuarioSolicitud {
    @Id
    private Integer id;
    private String nombre;
    private Integer usuarioid;
    private Integer solicitudid;
    private String email;
}
