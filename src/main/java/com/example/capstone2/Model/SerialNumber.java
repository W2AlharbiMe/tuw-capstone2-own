package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "serial_numbers")
public class SerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "the serial number field is required.")
    @Size(min = 17, max = 17, message = "the serial number must be 17 chars.")
    @Pattern(message = "the serial number must be like: '1ftfw1et4bf445903', the first 8 must contain chars and numbers then 1 either a digit or a capital letter X, next a 2 chars, finally 6 digits.", regexp = "^(?=.*\\d|[A-z])(?=.*[A-z])[A-z0-9]{17}$")
    @Column(nullable = false, unique = true)
    private String serialNumber;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "serialNumber")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private SalesInvoice salesInvoice;
//
//    @NotNull(message = "the car id field is required.")
//    @Column(nullable = false)
//    private Integer carId;

    // first part:
    // 1b2v3c4v
    // second part:
    // X
    // third part:
    // na
    // last part:
    // 123456
    // all combined: 1b2v3c4vXna123456

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean isUsed = false;


    // Relations

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


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

        serialNumber = generated;
    }
}
