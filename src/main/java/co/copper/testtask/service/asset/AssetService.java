package co.copper.testtask.service.asset;

import java.util.Optional;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.model.Asset;

//todo - Unjustified usage of an interface with single implementation is plain wrong since this violates YAGNI
public interface AssetService {
    //    todo - inconsistency - why Collateral interface is not used?
    boolean approve(AssetDto dto);

    Asset save(Asset asset);
    Optional<Asset> load(Long id);
    //todo - mapping should not be part of Service - single responsibility principle
    Asset fromDto(AssetDto dto);
    AssetDto toDTO(Asset asset);
    //todo - no point having this method since just receives an object and returns it's field
    Long getId(Asset asset);
}
