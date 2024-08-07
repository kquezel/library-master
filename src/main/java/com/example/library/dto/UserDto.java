package com.example.library.dto;

import com.example.library.model.enums.Role;
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
public class UserDto {
    @NotEmpty
    @Size(min = 2, max = 50)
    private String fullName;
    @NotEmpty
    @Size(min = 3, max = 255)
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Pattern(regexp = "(^0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[0-2]).(\\d{4}$)", message = "{datetime.format.error}")
    private String birth;
    private Role type;
}
