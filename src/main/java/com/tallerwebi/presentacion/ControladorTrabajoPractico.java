package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTrabajoPractico; // (O el servicio que uses)
import com.tallerwebi.dominio.TrabajoPractico;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTrabajoPractico {

  private ServicioTrabajoPractico servicioTrabajoPractico;

  @Autowired
  public ControladorTrabajoPractico(ServicioTrabajoPractico servicioTrabajoPractico) {
    this.servicioTrabajoPractico = servicioTrabajoPractico;
  }

  @RequestMapping(path = "/trabajos-practicos", method = RequestMethod.GET)
  public ModelAndView listarTrabajosPracticos(
    @RequestParam(value = "materia", required = false) String materia
  ) {
    Map<String, Object> modelo = new ModelMap();
    List<TrabajoPractico> listaTps;

    if (materia != null && !materia.isEmpty()) {
      listaTps = servicioTrabajoPractico.buscarPorMateria(materia);
    } else {
      listaTps = servicioTrabajoPractico.obtenerTodos();
    }

    modelo.put("trabajosPracticos", listaTps);
    return new ModelAndView("trabajos-practicos", modelo);
  }

  @RequestMapping(path = "/nuevo-trabajo-practico", method = RequestMethod.GET)
  public ModelAndView irANuevoTrabajoPractico() {
    Map<String, Object> modelo = new ModelMap();
    modelo.put("trabajoPractico", new TrabajoPractico());
    return new ModelAndView("nuevo-trabajo-practico", modelo);
  }

  @RequestMapping(path = "/guardar-trabajo-practico", method = RequestMethod.POST)
  public ModelAndView guardarTrabajoPractico(
    @ModelAttribute("trabajoPractico") TrabajoPractico trabajoPractico
  ) {
    servicioTrabajoPractico.crearTrabajoPractico(trabajoPractico);
    return new ModelAndView("redirect:/trabajos-practicos");
  }
}
