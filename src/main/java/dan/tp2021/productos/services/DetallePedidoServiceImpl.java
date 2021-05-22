package dan.tp2021.productos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dan.tp2021.productos.dao.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

	@Autowired
	DetallePedidoRepository detallePedidoRepository;
	
	@Override
	@Transactional
	public void setNullMateriales(Integer idMaterial) {
		detallePedidoRepository.setNullMaterialInDetalle(idMaterial);
	}

}
