package co.copper.testtask.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

@Service
@Slf4j
public class ExternalApproveService {

  //todo - quick and dirty, need to use Converter, also properties better handle via ConfigurationProperties
  @Value("${application.min-assess-date}")
  private String MIN_ASSESS_DATE;

  @Value("${application.min-asset-year}")
  private int MIN_ASSET_YEAR;

  @Value("${application.min-asset-value}")
  private BigDecimal MIN_ASSET_VALUE;


  public int approve(CollateralObject object) {

    // todo - should be handled by Bean Validation Api (@NotNull set in dto)
    if (object.getDate() == null
        || object.getYear() == null
        || object.getValue() == null
        || object.getType() == null) {
      return -1;
    }

    int code;
    switch (object.getType()) {
        // todo - should be handled on Design Pattern level - like with Command pattern.
        // Need to have a Handler interface with different implementations
      case ASSET:
        code = approveAsset(object);
        break;
        // todo - no need in such codes. should throw an exception and exception handled by
        // @ExceptionHandler
      default:
        code = -100;
    }

    return code;
  }

  // todo - should be handled on Design Pattern level - like with Command pattern.
  // todo - parameter should be name as AssetAdapter asset
  private int approveAsset(CollateralObject object) {
    // todo - can be handled by Bean Validation Api
    if (object.getYear() < MIN_ASSET_YEAR) {
      // todo - no need in such codes. should throw an exception and exception handled by
      // @ExceptionHandler
      return -10;
    }
    // todo - can be handled by Bean Validation Api
    if (object.getDate().isBefore(LocalDate.parse(MIN_ASSESS_DATE))) {
      // todo - no need in such codes. should throw an exception and exception handled by
      // @ExceptionHandler
      return -11;
    }
    // todo - can be handled by Bean Validation Api
    if (object.getValue().compareTo(MIN_ASSET_VALUE) < 0) {
      // todo - no need in such codes. should throw an exception and exception handled by
      // @ExceptionHandler
      return -12;
    }

    return 0;
  }
}
