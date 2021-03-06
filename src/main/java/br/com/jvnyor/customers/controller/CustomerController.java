package br.com.jvnyor.customers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jvnyor.customers.model.Customer;
import br.com.jvnyor.customers.model.CustomerPutDTO;
import br.com.jvnyor.customers.model.CustomerResponse;
import br.com.jvnyor.customers.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "CRUD list Customers")
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> listCustomers() {
		return ResponseEntity.ok(customerService.listAll());
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<CustomerResponse> findCustomerByCpf(@PathVariable String cpf) {
		return ResponseEntity.ok(customerService.findByCpfReturnsCustomerResponse(cpf));
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> saveCustomer(@RequestBody Customer customer) {
		return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
	}

	@PutMapping("/{cpf}")
	public ResponseEntity<CustomerResponse> replaceCustomer(@PathVariable String cpf, @RequestBody CustomerPutDTO customer) {
		return ResponseEntity.ok(customerService.replace(cpf, customer));
	}

	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String cpf) {
		customerService.delete(cpf);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
