package br.com.jvnyor.customers.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@NotNull(message = "The CPF cannot be null")
	@NotEmpty(message = "The CPF cannot be empty")
	@Schema(description = "CPF Number of customer", example = "12345678910", required = true)
	private String cpf;

	@NotNull(message = "The Name cannot be null")
	@NotEmpty(message = "The Name cannot be empty")
	@Schema(description = "first name of customer", example = "Nome", required = true)
	private String firstName;

	@NotNull(message = "The Name cannot be null")
	@NotEmpty(message = "The Name cannot be empty")
	@Schema(description = "last name of customer", example = "Sobrenome", required = true)
	private String lastName;

	@Schema(accessMode = AccessMode.READ_ONLY, example = "dd-MM-yyyy HH:mm:ss")
	private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
}
