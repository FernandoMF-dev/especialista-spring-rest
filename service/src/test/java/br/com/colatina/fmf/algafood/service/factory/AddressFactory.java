package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressFactory {
	@Autowired
	CityFactory cityFactory;

	public Address createEntity() {
		Address address = new Address();
		address.setCep(String.valueOf(System.currentTimeMillis()).substring(0, 5) + "-" + String.valueOf(System.currentTimeMillis()).substring(0, 3));
		address.setPublicSpace(String.format("Public Space %d", System.currentTimeMillis()));
		address.setStreetNumber(String.format("Street Number %d", System.currentTimeMillis()).substring(0, 20));
		address.setComplement(String.format("Complement %d", System.currentTimeMillis()));
		address.setDistrict(String.format("District %d", System.currentTimeMillis()));
		address.setCity(cityFactory.createAndPersist());
		return address;
	}

}
