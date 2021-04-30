package dan.tp2021.productos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.domain.Provision;

@Service
public interface ProvisionService {
	public ResponseEntity<Provision> getProvisionById(Integer id);
	public ResponseEntity<List<Provision>> getListaProvisiones();
	public ResponseEntity<Provision> saveProvision(Provision p);
	public ResponseEntity<Provision> deleteProvisionById(Integer id);
}
