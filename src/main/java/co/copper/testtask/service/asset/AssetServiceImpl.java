package co.copper.testtask.service.asset;

import java.util.Optional;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.external.ExternalApproveService;
import co.copper.testtask.model.Asset;
import co.copper.testtask.repository.AssetRepository;
import org.springframework.stereotype.Service;


@Service
public class AssetServiceImpl implements AssetService {
    private final ExternalApproveService approveService;

    private final AssetRepository assetRepository;

    public AssetServiceImpl(ExternalApproveService approveService, AssetRepository assetRepository) {
        this.approveService = approveService;
        this.assetRepository = assetRepository;
    }

    @Override
//    todo - inconsistency - why Collateral interface is not used?
//    todo - naming could be better - like isApproved()
    public boolean approve(AssetDto dto) {
        //todo - not much reason to use additional layer of abstaction for AssetAdapater
        return approveService.approve(new AssetAdapter(dto)) == 0;
    }

    @Override
    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    //todo - unnecessary method, also could be private
    @Override
    public Optional<Asset> load(Long id) {
        return assetRepository.findById(id);
    }

    //todo - mapping should not be part of Service - single responsibility principle
    @Override
    public Asset fromDto(AssetDto dto) {
        return new Asset(
                dto.getId(),
                dto.getName(),
                dto.getCurrency(),
                dto.getYear(),
                dto.getValue()
        );
    }

    //todo - mapping should not be part of Service - single responsibility principle
    @Override
    public AssetDto toDTO(Asset asset) {
//        todo - use Builder pattern for DTOs
        return new AssetDto(
                asset.getId(),
                asset.getName(),
                asset.getCurrency(),
                asset.getYear(),
                asset.getValue()
        );
    }

    //todo - no point having this method since just receives an object and returns it's field
    @Override
    public Long getId(Asset asset) {
        return asset.getId();
    }
}
