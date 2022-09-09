package africa.semicolon.parceldelivery.controllers;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.requests.ParcelCreationRequest;
import africa.semicolon.parceldelivery.services.ParcelService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/parcel")
@Slf4j
public class ParcelController {
    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PatchMapping("/{parcelId}")
    public ResponseEntity<?> updateParcelDeliveryStatus(@PathVariable Long parcelId, @RequestParam String status) {
        parcelService.updateParcelStatus(parcelId, status);
        var displayMessage = "Successful!";
        return new ResponseEntity<>(displayMessage, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/delivered")
    public ResponseEntity<?> getAllDeliveredParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllDeliveredParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/in-transit")
    public ResponseEntity<?> getAllParcelsInTransit(@RequestParam int pageNumber) {
        var list = parcelService.getAllParcelsInTransit(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllPendingParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/failed")
    public ResponseEntity<?> getAllFailedParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllFailedParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createParcel(@RequestBody ParcelCreationRequest request) {
        parcelService.createParcel(request);
        var body = "Your parcel has been created successfully";
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{parcel_id}")
    public ResponseEntity<?> getParcelDetails(@PathVariable Long parcel_id) {
        parcelService.getParcelDetails(parcel_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{parcel_id}")
    public ResponseEntity<?> updateParcelLocation(@PathVariable Long parcel_id, @RequestBody Location location) {
        parcelService.updateParcelLocation(parcel_id, location);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
