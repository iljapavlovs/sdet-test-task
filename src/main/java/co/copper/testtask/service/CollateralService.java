package co.copper.testtask.service;

import java.util.Optional;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.dto.CollateralDto;
import co.copper.testtask.service.asset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// TODO: reimplement this
@Service
public class CollateralService {
    private final AssetService assetService;

    public CollateralService(AssetService assetService) {
        this.assetService = assetService;
    }

    public Long createCollateral(CollateralDto collateralDto) {
        //todo - this is really bad
        if (!(collateralDto instanceof AssetDto)) {
            //todo - need to throw Application specific exception
            throw new IllegalArgumentException();
        }

        final var asset = (AssetDto) collateralDto;
        final var approved = assetService.approve(asset);
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
    public CollateralDto getCollateralById(String id) {
        return Optional.of(Long.parseLong(id))
                .flatMap(assetService::load)
                .map(assetService::toDTO)
                //todo - should throw an Application specific exception from here, not from Controller
                .orElse(null);
    }
}
