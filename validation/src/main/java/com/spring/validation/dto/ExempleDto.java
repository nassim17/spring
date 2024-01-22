package com.spring.validation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ExempleDto {

    @NotNull(message = "Le nom ne peut pas être nul")
    @Size(min = 2, max = 50, message = "Le nom doit avoir entre 2 et 50 caractères")
    private String nom;

    @NotEmpty(message = "La description ne peut pas être vide")
    private String description;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private int quantite;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private double prix;

    @Email(message = "L'adresse email doit être valide")
    private String email;

    @Min(1)
    @Max(10)
    private int numberBetweenOneAndTen;

    @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
    private String ipAddress;
}
