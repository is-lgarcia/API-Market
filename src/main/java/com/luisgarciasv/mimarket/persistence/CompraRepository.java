package com.luisgarciasv.mimarket.persistence;


import com.luisgarciasv.mimarket.domain.Purchase;
import com.luisgarciasv.mimarket.domain.repository.PurchaseRepository;
import com.luisgarciasv.mimarket.persistence.crud.CompraCrudRepository;
import com.luisgarciasv.mimarket.persistence.entity.Compra;
import com.luisgarciasv.mimarket.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {

        System.out.println("ClientId: " + purchase.getClientId());
        System.out.println("Comment: "+purchase.getComment());
        System.out.println("Date: "+purchase.getDate().toString());
        System.out.println("Item 1: "+purchase.getItems().get(0).getProductId());
        System.out.println("PayMethod: "+purchase.getPaymentMethod());
        System.out.println("State: "+purchase.getState());
        System.out.println("CompraId: "+purchase.getPurchaseId());


        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
