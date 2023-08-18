package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "the model field is required.")
    @Size(min = 4, message = "the model must be at least 4 chars.")
    @Column(nullable = false)
    private String model;

    /**
     * Sedan
     * Sport
     * Van
     * Hatchback
     * SUV
     * Minivan
     * Truck
     */
    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `sedan`, `sport`, `van`, `hatchback`, `suv`, `minivan`, `truck`.", regexp = "(?i)\\b(sedan|sport|van|hatchback|suv|minivan|truck)\\b?")
    @Column(columnDefinition = "varchar(9) not null check (type in ('sedan', 'sport', 'van', 'hatchback', 'suv', 'minivan', 'truck'))")
    private String type;

    @NotEmpty(message = "the color field is required.")
    @Column(nullable = false)
    private String color;


    @NotNull(message = "the serial number field is required.")
    @Size(min = 17, max = 17, message = "the serial number must be 17 chars.")
    @Pattern(message = "the serial number must be like: '1ftfw1et4bf445903', the first 8 must contain chars and numbers then 1 either a digit or a capital letter X, next a 2 chars, finally 6 digits.", regexp = "^(?=.*\\d|[A-z])(?=.*[A-z])[A-z0-9]{17}$")
    @Column(nullable = false, unique = true)
    private String serialNumber;

    // first part:
    // 1b2v3c4v
    // second part:
    // X
    // third part:
    // na
    // last part:
    // 123456
    // all combined: 1b2v3c4vXna123456 1ftfw1et

    @NotNull(message = "the seats count is required.")
    @Positive(message = "the seats count must be positive number.")
    @Column(nullable = false)
    private Integer seatsCount;

    @NotEmpty(message = "the release year field is required.")
    @Digits(integer = 4, fraction = 0, message = "the release year must be 4 digits.")
    @Column(nullable = false)
    private String releaseYear;

    @NotNull(message = "the manufacturer id field is required.")
    @Column(nullable = false)
    private Integer manufacturerId;

    // this should generate unique VIN.
    public void generateSerialNumber() {
        String generated = "";
        char[] chars = {
                'A', 'b', 'c',
                'd', 'E', 'F',
                'J', 'K', 'n',
                'a', 'b', 'c', 'd', 'j', 'f', 'k'
        };
        char X = 'X';
        int second_part = (int) Math.floor(Math.random() * 10);

        // first part 8 chars
        for (int i = 1; i <= 8; i++) {
            int random_char_index = (int) Math.floor(Math.random() * chars.length);
            generated += chars[random_char_index];
        }

        // second part either char 'X' or a digit
        if(second_part > 11) {
            generated += X;
        } else {
            generated += second_part;
        }

        // third part:
        for (int i = 0; i < 3; i++) {
            int random_char_index = (int) Math.floor(Math.random() * chars.length);
            generated += chars[random_char_index];
        }

        // last part
        for (int i = 0; i < 5; i++) {
            int last_part = (int) Math.floor(Math.random() * 10);
            generated += last_part;
        }

        System.out.println(generated);

        serialNumber = generated;
    }
}
