package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Provision;


public interface ProvisionService {

	class ProvisionException extends Exception { ProvisionException(String message){super(message);}}
	class ProvisionNotFoundException extends ProvisionException { ProvisionNotFoundException(String message){super(message);}}

	Provision getProvisionById(Integer id) throws ProvisionException;
	List<Provision> getListaProvisiones();
	Provision saveProvision(Provision p) throws ProvisionException;
	Provision deleteProvisionById(Integer id) throws ProvisionException;
}
