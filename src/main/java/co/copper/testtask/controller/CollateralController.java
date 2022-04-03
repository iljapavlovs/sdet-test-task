package co.copper.testtask.controller;

import co.copper.testtask.dto.CollateralDto;
import co.copper.testtask.service.CollateralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/collaterals")
public class CollateralController {
    private final CollateralService service;

    public CollateralController(CollateralService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createCollateral(@RequestBody CollateralDto collateralDto) {
        Long id = service.createCollateral(collateralDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();

        return id != null ?  ResponseEntity.created(location).build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollateralDto> getCollateral(@PathVariable String id) {
        CollateralDto collateralDto = service.getCollateralById(id);
        return collateralDto != null ? ResponseEntity.ok(collateralDto) : ResponseEntity.notFound().build();
    }
}
