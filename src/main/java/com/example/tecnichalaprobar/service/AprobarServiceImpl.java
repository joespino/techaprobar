package com.example.tecnichalaprobar.service;

import com.example.tecnichalaprobar.model.GestorAprobador;
import com.example.tecnichalaprobar.model.UsuarioSolicitud;
import com.example.tecnichalaprobar.repository.GestorAprobadorRepository;
import com.example.tecnichalaprobar.repository.UsuarioSolicitudRepository;
import com.example.tecnichalaprobar.util.Utilitarios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class AprobarServiceImpl implements AprobarService {

    private static final Logger LOG = LoggerFactory.getLogger(AprobarServiceImpl.class);
    private final GestorAprobadorRepository gestorAprobadorRepository;
    private final UsuarioSolicitudRepository usuarioSolicitudRepository;
    private final String url = "https://technicalspike.s3.amazonaws.com/plantilla/";

    @Autowired
    private TemplateEngine templateEngine;

    public AprobarServiceImpl(GestorAprobadorRepository gestorAprobadorRepository, UsuarioSolicitudRepository usuarioSolicitudRepository) {
        this.gestorAprobadorRepository = gestorAprobadorRepository;
        this.usuarioSolicitudRepository = usuarioSolicitudRepository;
    }

    @Override
    public String aprobarSolicitud(Map<String, String> params) {
        try {
            final Context ctx = new Context();
            String usuario = Utilitarios.desencriptar(params.get("usuario"));
            String solicitud = Utilitarios.desencriptar(params.get("solicitud"));
            boolean aprobado = Boolean.parseBoolean(params.get("aprobado"));
            GestorAprobador datos = gestorAprobadorRepository.findGestorAprobadorBySolicitudid(
                    Integer.parseInt(solicitud)
            );
            if(!ObjectUtils.isEmpty(datos)) {
                if(ObjectUtils.isEmpty(datos.getEmitioaccion())) {
                    if(LocalDate.now().isAfter(datos.getFechaexpiracion().toLocalDate()))
                    {
                        ctx.setVariable("solicitud", datos.getSolicitudid());
                        return this.templateEngine.process(url + "plantillatiempo.html", ctx);
                    } else
                    {
                        Optional<UsuarioSolicitud> usuarioSolicitud = usuarioSolicitudRepository.
                                findUsuarioSolicitudByUsuarioid(Integer.parseInt(usuario));
                        if(usuarioSolicitud.isPresent()) {
                            gestorAprobadorRepository.save(GestorAprobador
                                    .builder()
                                    .id(datos.getId())
                                    .nombre(usuarioSolicitud.get().getNombre())
                                    .usuarioid(usuarioSolicitud.get().getUsuarioid())
                                    .solicitudid(datos.getSolicitudid())
                                    .emitioaccion(1)
                                    .aprobado(aprobado ? 1 : 0)
                                    .build());
                            ctx.setVariable("solicitud", datos.getSolicitudid());
                            ctx.setVariable("aprobado", datos.getAprobado() == 1 ? "Aprobado" : "Rechazado");
                            return this.templateEngine.process(url + "plantillaprocesado.html", ctx);
                        }

                    }
                } else {
                    ctx.setVariable("solicitud", datos.getSolicitudid());
                    ctx.setVariable("aprobado", datos.getAprobado() == 1 ? "Aprobado" : "Rechazado");
                    ctx.setVariable("nombre", datos.getNombre());
                    return this.templateEngine.process(url + "plantillaaceptado.html", ctx);
                }
            }
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return null;
    }
}
