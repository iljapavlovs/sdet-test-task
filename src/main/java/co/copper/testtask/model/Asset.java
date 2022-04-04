package co.copper.testtask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//todo - using @Data on JPA entities is not advised (https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ASSET")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //todo - use Currency type
    private Currency currency;

    @Column(name = "year_of_issue")
    private Short year;

    @Column(name = "assessed_value")
    private BigDecimal value;
}
