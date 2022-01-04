package br.com.jvnyor.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.jvnyor.customers.model.Customer;

@Service
public class CustomerService {
	
	private List<Customer> customers = new ArrayList<>();
	
	public List<Customer> listAll() {
		return customers;
	}
	
	public Customer findByCpf(String cpf) {
		return customers.stream()
				.filter(customer -> customer.getCpf().equals(cpf))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer not found"));
	}
	
	public Customer save(Customer customer) {
		if (cpfExist(customer.getCpf())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf already exist");
		}
		if (customer.getCpf().length() != 11) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF format are incorrect");
		} else if (!stringIsNumeric(customer.getCpf())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF format are incorrect");
		} else if (customer.getName().length()<10) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FIRST NAME, MIDDLE NAME AND LAST NAME");
		} else if (!stringIsCharacter(customer.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name format are incorrect");
		} else {
			customers.add(customer);
			return customer;
		}
		
	}
	
	public Customer replace(Customer customer) {
		Customer customerSaved = findByCpf(customer.getCpf());
		if (!stringIsCharacter(customer.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name format are incorrect");
		} else if (customer.getName().length()<10) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FIRST NAME, MIDDLE NAME AND LAST NAME");
		} else {
			customerSaved.setName(customer.getName());
			return customerSaved;
		}
	}
	
	public void delete(String cpf) {
		customers.remove(findByCpf(cpf));
	}
	
	public boolean cpfExist(String cpf) {
		boolean check = false;
		List<Customer> collect = customers
				.stream()
					.filter(
							(Customer c) -> cpf.equals(c.getCpf())).collect(Collectors.toList());
		if (!collect.isEmpty()) {
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