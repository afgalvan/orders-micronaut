package tech.afgalvan.products.data;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import tech.afgalvan.products.models.Product;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProductsRepository extends CrudRepository<Product, Integer> {

    default Stream<Product> asStream() {
        return StreamSupport.stream(findAll().spliterator(), false);
    }

    default List<Product> asList() {
        return asStream().toList();
    }
}
