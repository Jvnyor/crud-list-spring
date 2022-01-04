package br.com.jvnyor.customers.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@NotNull(message = "The CPF cannot be null")
	@NotEmpty(message="The CPF cannot be empty")
	@Schema(description = "CPF Number of customer", example = "12345678910", required = true)
	private String cpf;
	@NotNull(message = "The Name cannot be null")
	@NotEmpty(message="The Name cannot be empty")
	@Schema(description = "Name of customer", example = "Josias Junior", required = true)
	private String name;
}
