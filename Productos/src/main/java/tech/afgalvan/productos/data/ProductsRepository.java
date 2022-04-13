package tech.afgalvan.productos.data;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import tech.afgalvan.productos.models.Product;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProductsRepository extends CrudRepository<Product, Integer> {
}
