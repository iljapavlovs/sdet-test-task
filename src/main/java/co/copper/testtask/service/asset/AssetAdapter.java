package co.copper.testtask.service.asset;

import java.math.BigDecimal;
import java.time.LocalDate;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.external.CollateralObject;
import co.copper.testtask.external.CollateralType;
import lombok.AllArgsConstructor;

//todo - An Adapter pattern acts as a connector between two incompatible interfaces that otherwise cannot be connected directly.
// So no reason o use it, as it does not provides any additional value (so it is an adapter to AssetDto, which is totally unnecessary)
@AllArgsConstructor
public class AssetAdapter implements CollateralObject {
    private AssetDto asset;

    @Override
    public BigDecimal getValue() {
        return asset.getValue();
    }

    @Override
    public Short getYear() {
        return asset.getYear();
    }

    @Override
    public LocalDate getDate() {
        //todo - should use Clock in order to test it more conveniently
        // todo - should set Clock as a global bean with UTC as a timezone
        //todo - better also provide this date from the client
        // take now date for all assets
        return LocalDate.now();
    }

    //todo - ? - what if there will be other CollateralType
    @Override
    public CollateralType getType() {
        return CollateralType.ASSET;
    }
}
