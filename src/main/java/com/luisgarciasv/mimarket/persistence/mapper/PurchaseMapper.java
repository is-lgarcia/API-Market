package com.luisgarciasv.mimarket.persistence.mapper;

import com.luisgarciasv.mimarket.domain.Purchase;
import com.luisgarciasv.mimarket.domain.PurchaseItem;
import com.luisgarciasv.mimarket.persistence.entity.Compra;
import com.luisgarciasv.mimarket.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PurchaseItem.class})
public interface PurchaseMapper {

    @Mappings({
            @Mapping(source = "idCompra",target = "purchaseId"),
            @Mapping(source = "idCliente",target = "clientId"),
            @Mapping(source = "fecha",target = "date"),
            @Mapping(source = "medioPago",target = "paymentMethod"),
            @Mapping(source = "comentario",target = "comment"),
            @Mapping(source = "estado",target = "state"),
            @Mapping(source = "productos",target = "items")
    })
    Purchase toPurchase(Compra compra);
    List<Purchase> toPurchases(List<Compra> compras);

    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
    Compra toCompra (Purchase purchase);
}