package tech.afgalvan.productos.controllers.commands;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CreateProductCommand {
    private String name;
    private int price;

    public CreateProductCommand(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public CreateProductCommand() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
