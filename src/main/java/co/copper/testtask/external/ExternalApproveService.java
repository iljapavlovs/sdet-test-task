package co.copper.testtask.external;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ExternalApproveService {
    //todo - should be parametrized in config file (properties)
    private static final LocalDate MIN_ASSESS_DATE = LocalDate.of(2017, Month.OCTOBER, 1);
    private static final int MIN_ASSET_YEAR = 2000;
    private static final BigDecimal MIN_ASSET_VALUE = BigDecimal.valueOf(1_000_000);

    public int approve(CollateralObject object) {
        // todo - should be handled by Bean Validation Api (@NotNull set in dto)
        if (object.getDate() == null ||object.getYear() == null || object.getValue() == null || object.getType() == null) {
            return -1;
        }

        int code;
        switch (object.getType()) {
            //todo - should be handled on Design Pattern level - like with Command pattern.
            // Need to have a Handler interface with different implementations
            case ASSET: code = approveAsset(object); break;
            //todo - no need in such codes. should throw an exception and exception handled by @ExceptionHandler
            default: code = -100;
        }

        return code;
    }

    //todo - should be handled on Design Pattern level - like with Command pattern.
    //todo - parameter should be name as AssetAdapter asset
    private int approveAsset(CollateralObject object) {
        // todo - can be handled by Bean Validation Api
        if (object.getYear() < MIN_ASSET_YEAR) {
            //todo - no need in such codes. should throw an exception and exception handled by @ExceptionHandler
            return -10;
        }
        // todo - can be handled by Bean Validation Api
        if (object.getDate().isBefore(MIN_ASSESS_DATE)) {
            //todo - no need in such codes. should throw an exception and exception handled by @ExceptionHandler
            return -11;
        }
        // todo - can be handled by Bean Validation Api
        if (object.getValue().compareTo(MIN_ASSET_VALUE) < 0) {
            //todo - no need in such codes. should throw an exception and exception handled by @ExceptionHandler
            return -12;
        }

        return 0;
    }
}
