package com.sep.psp.controller;

import com.sep.psp.dto.OfferDTO;
import com.sep.psp.model.Offer;
import com.sep.psp.service.definition.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/offer")
public class OfferController {
    @Autowired
    private OfferService offerService;

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws NoSuchAlgorithmException, KeyManagementException {
        Offer o = offerService.get(id);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping(path = "/offers")
    public ResponseEntity<?> getAll() {
        List<Offer> offers = offerService.getAllNotDeleted();

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping(path = "/addOffer")
    public ResponseEntity<?> add(@RequestBody OfferDTO dto) throws NoSuchAlgorithmException, KeyManagementException {
        logger.info("Received request to add offer: {}", dto);
        Offer o = offerService.add(dto);

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean delete = offerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
