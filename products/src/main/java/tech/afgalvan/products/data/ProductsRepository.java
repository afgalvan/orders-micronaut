package tech.afgalvan.products.data;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import tech.afgalvan.products.models.Product;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProductsRepository extends CrudRepository<Product, Integer> {
}
