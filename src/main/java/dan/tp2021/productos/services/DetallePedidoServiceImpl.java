package dan.tp2021.productos.services;

import dan.tp2021.productos.domain.DetallePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dan.tp2021.productos.dao.DetallePedidoRepository;

import java.util.Optional;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

	@Autowired
	DetallePedidoRepository detallePedidoRepository;
	
	@Override
	@Transactional
	public void setNullMateriales(Integer idMaterial) {
		detallePedidoRepository.setNullMaterialInDetalle(idMaterial);
	}

	@Override
	public DetallePedido findById(Integer id) throws Exception {
		Optional<DetallePedido> detallePedidoOptional = detallePedidoRepository.findById(id);
		if(detallePedidoOptional.isPresent()){
			return detallePedidoOptional.get();
		}
		throw new Exception("No se encontro el detalle con id: "+id);
	}
}
