package co.copper.testtask.service;

import java.util.Optional;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.dto.Collateral;
import co.copper.testtask.service.asset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// TODO: reimplement this
@Service
public class CollateralService {
    //todo - Field Injection is Evil
    @Autowired
    private AssetService assetService;

    //todo can be removed
    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        //todo - this is really bad
        if (!(object instanceof AssetDto)) {
            //todo - need to throw Application specific exception
            throw new IllegalArgumentException();
        }
        //todo - use `final var` instead
        AssetDto asset = (AssetDto) object;
        //todo - use `final var` instead
        boolean approved = assetService.approve(asset);
        //todo - should throw an Application specific exception from here, not from Controller. And handled by @ExceptionHandler
        if (!approved) {
            return null;
        }

        //todo - should throw an Application specific exception from here, not from Controller. And handled by @ExceptionHandler
        return Optional.of(asset)
                .map(assetService::fromDto)
                .map(assetService::save)
                .map(assetService::getId)
                .orElse(null);
    }

    //todo - naming could be better
    public Collateral getInfo(String id) {
        return Optional.of(Long.parseLong(id))
                .flatMap(assetService::load)
                .map(assetService::toDTO)
                //todo - should throw an Application specific exception from here, not from Controller
                .orElse(null);
    }
}
