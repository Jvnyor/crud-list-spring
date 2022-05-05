package br.com.jvnyor.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private List<CustomerResponse> customersResponseList = new ArrayList<>();
	
	public List<CustomerResponse> listAll() {
		return customersResponseList;
	}

	public CustomerResponse findByCpfReturnsCustomerResponse(String cpf) {
		
		return customersResponseList.stream()
				.filter(customerResponse -> customerResponse.getCpf().equals(cpf))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
	}
	
	public Customer findByCpfReturnsCustomer(String cpf) {
		
		return customers.stream()
				.filter(customer -> customer.getCpf().equals(cpf))
				.findFirst()
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
			for (Customer c : customers) {
				
				if (customer.getCpf().equals(c.getCpf())) {
					
					CustomerResponse customerResponse = CustomerResponse.builder()
							.cpf(c.getCpf())
							.fullName(c.getFirstName() + " " + c.getLastName())
							.createdAt(c.getCreatedAt())
							.build();
					
					customersResponseList.add(customerResponse);
				}
			}
			log.info("Customer saved in memory: {}", customer);

			return CustomerResponse.builder()
					.cpf(customer.getCpf())
					.fullName(customer.getFirstName().concat(" ").concat(customer.getLastName()))
					.createdAt(customer.getCreatedAt())
					.build();
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

			for (Customer c : customers) {
				
				if (customerSaved.getCpf().equals(c.getCpf())) {
					
					findByCpfReturnsCustomerResponse(customerSaved.getCpf())
							.setFullName(customerSaved.getFirstName().concat(" ").concat(customerSaved.getLastName()));
					
				}
			}
			
			log.info("Customer replaced in memory {}", customerSaved);
			
			return CustomerResponse.builder()
					.cpf(customerSaved.getCpf())
					.fullName(customerSaved.getFirstName().concat(" ").concat(customerSaved.getLastName()))
					.createdAt(customerSaved.getCreatedAt())
					.build();
		}
	}

	public void delete(String cpf) {
		customers.remove(findByCpfReturnsCustomer(cpf));
		customersResponseList.remove(findByCpfReturnsCustomerResponse(cpf));
	}

	public boolean cpfExist(String cpf) {
		boolean check = false;
		
		Optional<Customer> customerCpfExists = customers.stream()
				.filter(customer -> cpf.equals(customer.getCpf()))
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