package com.luisgarciasv.mimarket.persistence;

import com.luisgarciasv.mimarket.domain.Product;
import com.luisgarciasv.mimarket.domain.repository.ProductRepository;
import com.luisgarciasv.mimarket.persistence.crud.ProductoCrudRepository;
import com.luisgarciasv.mimarket.persistence.entity.Producto;
import com.luisgarciasv.mimarket.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/*
*Con la anotacion Repository estamos indicando que esta clase estará
*teniendo relacion con la base de datos o manejando la base de datos.
* */
@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    public Producto save(Producto producto){
        return productoCrudRepository.save(producto);
    }

    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }
}
