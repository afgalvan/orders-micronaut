package Pedidos;

import io.micronaut.http.annotation.*;

@Controller("/pedidos")
public class PedidosController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}