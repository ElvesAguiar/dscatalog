package com.elves.dscatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordDTO {
    @NotBlank(message = "Campo obrigatorio")
    private String token;
    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 8,message = "Campo obrigatorio")
    private String password;
}
