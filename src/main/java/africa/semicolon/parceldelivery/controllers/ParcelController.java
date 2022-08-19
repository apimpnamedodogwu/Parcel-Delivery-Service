package africa.semicolon.parceldelivery.controllers;

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

    @PatchMapping("/parcel-status/new-status/{parcel_id}")
    public ResponseEntity<?> updateParcelDeliveryStatus(@PathVariable Long parcel_id, @RequestParam String status) {
        parcelService.updateParcelStatus(parcel_id, status);
        var displayMessage = "Successful!";
        return new ResponseEntity<>(displayMessage, HttpStatus.OK);
    }

    @GetMapping("/get-all-parcels/page-number")
    public ResponseEntity<?> getAllParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get-all-delivered-parcels/page-number")
    public ResponseEntity<?> getAllDeliveredParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllDeliveredParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get-all-parcels-in-transit/page-number")
    public ResponseEntity<?> getAllParcelsInTransit(@RequestParam int pageNumber) {
        var list = parcelService.getAllParcelsInTransit(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get-all-pending-parcels/page-number")
    public ResponseEntity<?> getAllPendingParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllPendingParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get-all-failed-to-deliver-parcels/page-number")
    public ResponseEntity<?> getAllFailedParcels(@RequestParam int pageNumber) {
        var list = parcelService.getAllFailedParcels(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
