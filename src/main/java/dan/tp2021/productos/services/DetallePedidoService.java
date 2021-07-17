package dan.tp2021.productos.services;

import dan.tp2021.productos.domain.DetallePedido;

import java.util.List;
import java.util.Optional;

public interface DetallePedidoService {

    DetallePedido findById(Integer id) throws Exception;

    List<DetallePedido> findByMaterialId(Integer materialId);
}
