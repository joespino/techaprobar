package com.example.tecnichalaprobar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_gestoraprobador")
public class GestorAprobador {

    @Id
    private Integer id;
    private String nombre;
    private Integer usuarioid;
    private Integer emitioaccion;
    private Integer solicitudid;
    private Integer aprobado;
}
