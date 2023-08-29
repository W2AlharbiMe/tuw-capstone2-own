package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true) // this is used to take default initialized values into account.
@Entity(name = "sales_invoices")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false, updatable = false)
    private String invoiceUUID;


    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `full_car_payment`, or `instalment_car_payment`, `full_service_payment`, ``, `instalment_service_payment`.", regexp = "(?i)\\b(full_car_payment|instalment_car_payment|full_service_payment|instalment_service_payment)\\b?")
    @Column(columnDefinition = "varchar(18) not null check (type in ('full_car_payment', 'instalment_car_payment', 'full_service_payment', 'instalment_service_payment'))")
    private String type;

    @NotEmpty(message = "the status field is required.")
    @Pattern(message = "the status can only be `pending` or `paid`.", regexp = "(?i)\\b(pending|paid)\\b?")
    @Column(columnDefinition = "varchar(8) not null check (status in ('pending', 'paid'))")
    private String status;


    @PositiveOrZero(message = "the instalment per month can only be a 0 or positive number.")
    @Column()
    private Double instalmentPerMonth = 0.0; // when type is full_payment then default this to 0


    // this will be calculated automatically. by default 5% of sub car price
    @Column()
    private Double salesPersonBonus = 0.5;


    @Column()
    private Double vat = 0.15;


    // the following line will add the current time and show it within the response
    // if it does not exist the createdAt will be null in the response,
    // but in the database it will be the CURRENT_TIMESTAMP.
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // convert from database timestamp type to java.util.Date
    @Column(columnDefinition="timestamp not null default CURRENT_TIMESTAMP")
    private Date createdAt;






//    @NotNull(message = "the serial number id field is required.")
//    @Column()
//    private Integer serialNumberId;



    @NotNull(message = "the sub price field is required.")
    @Positive(message = "the sub price must be positive number.")
    @Column(nullable = false)
    private Double subPrice; // car price without vat


    // this will be handled by jackson, and it will show it in the response.
    public Double getTotalPrice() {
        return ((subPrice * vat) + subPrice);
    }


    // Relations

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    @ManyToOne
    @JoinColumn(name = "dealer_service_id", referencedColumnName = "id")
    private DealerService dealerService;


    @ManyToOne
    @JoinColumn(name = "sales_person_id", referencedColumnName = "user_id")
    private SalesPerson salesperson;


    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;


    @OneToOne
    @MapsId
    private SerialNumber serialNumber;
}
