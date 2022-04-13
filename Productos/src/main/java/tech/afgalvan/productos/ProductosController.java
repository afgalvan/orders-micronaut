package tech.afgalvan.productos;

import io.micronaut.http.annotation.*;

@Controller("/productos")
public class ProductosController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}