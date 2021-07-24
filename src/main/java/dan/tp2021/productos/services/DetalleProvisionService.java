package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.DetalleProvision;

public interface DetalleProvisionService {

    List<DetalleProvision> findByMaterialId(Integer materialId);

}
