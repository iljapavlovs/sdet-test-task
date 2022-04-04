package co.copper.testtask.repository;

import co.copper.testtask.model.Asset;
import org.springframework.data.repository.CrudRepository;

//todo - would name this CollateralRepository and stored the type in it
public interface AssetRepository extends CrudRepository<Asset, Long> {
}
