package dan.tp2021.productos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import dan.tp2021.productos.dao.DetalleProvisionRepository;
import dan.tp2021.productos.domain.DetalleProvision;

@Service
public class DetalleProvisionServiceImpl implements DetalleProvisionService{

    @Autowired
    DetalleProvisionRepository detalleProvisionRepository;

    @Override
    public List<DetalleProvision> findByMaterialId(Integer materialId) {
        return detalleProvisionRepository.findByMaterialId(materialId);
    }
}
