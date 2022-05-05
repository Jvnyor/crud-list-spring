package br.com.jvnyor.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.jvnyor.customers.model.Customer;
import br.com.jvnyor.customers.model.CustomerResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	private List<Customer> customers = new ArrayList<>();

	public List<CustomerResponse> listAll() {
		return customers.stream().map(c -> new CustomerResponse(c)).collect(Collectors.toList());
	}

	public CustomerResponse findByCpfReturnsCustomerResponse(String cpf) {
		return new CustomerResponse(findByCpfReturnsCustomer(cpf));
	}

	public Customer findByCpfReturnsCustomer(String cpf) {
		return customers.stream().filter(customer -> customer.getCpf().equals(cpf)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
	}

	public CustomerResponse save(Customer customer) {
		if (cpfExist(customer.getCpf())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf already exist");
		} else if (customer.getCpf().length() != 11) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF format are incorrect");
		} else if (!stringIsNumeric(customer.getCpf())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF format are incorrect");
		} else if (customer.getFirstName() == null || customer.getFirstName().isBlank()
				|| customer.getFirstName().isEmpty() && customer.getLastName() == null
				|| customer.getLastName().isBlank() || customer.getLastName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot empty or null");
		} else if (!stringIsCharacter(customer.getFirstName()) && !stringIsCharacter(customer.getLastName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name format are incorrect");
		} else {

			customers.add(customer);

			log.info("Customer saved in memory: {}", customer);

			return new CustomerResponse(customer);
		}

	}

	public CustomerResponse replace(Customer customer) {
		Customer customerSaved = findByCpfReturnsCustomer(customer.getCpf());

		if (!stringIsCharacter(customer.getFirstName()) && !stringIsCharacter(customer.getLastName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name format are incorrect");
		} else if (customer.getFirstName() == null || customer.getFirstName().isBlank()
				|| customer.getFirstName().isEmpty() && customer.getLastName() == null
				|| customer.getLastName().isBlank() || customer.getLastName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot empty or null");
		} else {
			customerSaved.setFirstName(customer.getFirstName());
			customerSaved.setLastName(customer.getLastName());

			log.info("Customer replaced in memory {}", customerSaved);

			return new CustomerResponse(customerSaved);
		}
	}

	public void delete(String cpf) {
		customers.remove(findByCpfReturnsCustomer(cpf));
	}

	public boolean cpfExist(String cpf) {
		boolean check = false;

		Optional<Customer> customerCpfExists = customers.stream().filter(customer -> cpf.equals(customer.getCpf()))
				.findFirst();

		if (customerCpfExists.isPresent()) {
			check = true;
		}

		return check;
	}

	public boolean stringIsNumeric(String string) {
		boolean isNumeric = true;
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isNumeric = false;
			}
		}
		return isNumeric;
	}

	public boolean stringIsCharacter(String string) {
		// TODO Auto-generated method stub
		boolean isCharacter = true;
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isAlphabetic(string.charAt(i)) && !Character.isSpaceChar(string.charAt(i))) {
				isCharacter = false;
			}
		}
		return isCharacter;
	}

}