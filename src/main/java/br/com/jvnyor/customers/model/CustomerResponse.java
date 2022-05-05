package br.com.jvnyor.customers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

	@Schema(example = "12345678910")
	private String cpf;

	@Schema(example = "Nome Sobrenome")
	private String fullName;

	@Schema(example = "dd-MM-yyyy HH:mm:ss")
	private String createdAt;

	public CustomerResponse(Customer customer) {
		this.cpf = customer.getCpf();
		this.fullName = customer.getFirstName().concat(" ").concat(customer.getLastName());
		this.createdAt = customer.getCreatedAt();
	}
}
