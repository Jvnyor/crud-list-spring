package br.com.jvnyor.customers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

	@Schema(example = "12345678910")
	private String cpf;

	@Schema(example = "Nome Sobrenome")
	private String fullName;

	@Schema(example = "dd-MM-yyyy HH:mm:ss")
	private String createdAt;
}
