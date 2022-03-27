package co.copper.testtask.external;

import java.math.BigDecimal;
import java.time.LocalDate;

//todo - naming is off - it is the Adapter interface for AssetAdapter
//todo - Unjustified usage of an interface with single implementation is plain wrong since this violates YAGNI
//todo - An Adapter pattern acts as a connector between two incompatible interfaces that otherwise cannot be connected directly.
// So no reason to use it, as it does not provides any additional value (so it is an adapter to AssetDto, which is totally unnecessary)
public interface CollateralObject {
    BigDecimal getValue();
    Short getYear();
    LocalDate getDate();
    CollateralType getType();
}
