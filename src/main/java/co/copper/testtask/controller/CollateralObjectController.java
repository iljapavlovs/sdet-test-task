package co.copper.testtask.controller;

import co.copper.testtask.dto.Collateral;
import co.copper.testtask.service.CollateralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//todo - fix rest paths
// todo - add /api/v1 to the path
@RestController
public class CollateralObjectController {
    //todo - Field Injection is Evil
    @Autowired
    private CollateralService service;

    @PostMapping("/collaterals")
    //todo - should not return HttpEntity, but more narrowed down class as ResponseEntity, or just a Model
    public HttpEntity<Long> save(@RequestBody Collateral object) {
        Long id = service.saveCollateral(object);
        //todo - by REST convention, POST should return Location header for the created resource
        //todo - should not be logic for response. This should be handled in service, throwing an exception.
        // Exceptions then should be handled by @ExceptionHandler providing desired status code and error message
        return id != null ? ResponseEntity.ok(id) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/collaterals/{id}")
    //todo - should not return HttpEntity, but more narrowed down class as ResponseEntity, or just a Model
    public HttpEntity<Collateral> getInfo(@PathVariable String id) {
        Collateral info = service.getInfo(id);
        //todo - should not be logic for response. This should be handled in service, throwing an exception.
        // Exceptions then should be handled by @ExceptionHandler providing desired status code and error message
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }
}

