package com.example.library.dto;

import com.example.library.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDto {
    private UUID guid;
    @NotEmpty
    @Size(min = 5, max = 50)
    private String fullName;
    @NotNull
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Pattern(regexp = "(^0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[0-2]).(\\d{4}$)", message = "{datetime.format.error}")
    private String birth;
    @NotEmpty
    private String biography;
}
