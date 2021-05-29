package dan.tp2021.productos.services;

import dan.tp2021.productos.domain.DetallePedido;

import java.util.Optional;

public interface DetallePedidoService {

	void setNullMateriales(Integer idMaterial);

    DetallePedido findById(Integer id) throws Exception;
}
