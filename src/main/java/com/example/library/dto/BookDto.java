package com.example.library.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private String guid;
    @NotEmpty
    @Size(min = 5, max = 50)
    private String name;
    @NotNull
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Pattern(regexp = "(^0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[0-2]).(\\d{4}$)", message = "{datetime.format.error}")
    private String publication;
    @NotEmpty
    @Min(value = 4, message = "размер должно быть не меньше 4")
    private String genre;
    @NotEmpty
    private String author;

}
