package co.copper.testtask.dto;

import java.math.BigDecimal;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//todo - use @Builder for simpler object creation
@Data
@NoArgsConstructor
@AllArgsConstructor
//todo no need in JsonTypeName (debatable)
@JsonTypeName("asset")
public class AssetDto implements CollateralDto {
    private Long id;
    private String name;
    //todo - currency and money usually abstracted into a separate object
    private Currency currency;
    private Short year;
    private BigDecimal value;
    //todo - need add private CollateralType collateralType explicitly (debatable)

}
