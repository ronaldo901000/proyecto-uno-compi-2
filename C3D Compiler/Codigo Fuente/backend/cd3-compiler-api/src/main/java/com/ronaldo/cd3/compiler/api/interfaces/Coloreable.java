package com.ronaldo.cd3.compiler.api.interfaces;

import com.ronaldo.cd3.compiler.api.dtos.colorToken.ColorTokenDTO;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public interface Coloreable {

    public List<ColorTokenDTO> generarColoreado(String texto, String opcion);
}
