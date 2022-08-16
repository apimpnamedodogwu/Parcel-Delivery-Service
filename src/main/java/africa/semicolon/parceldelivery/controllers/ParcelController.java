package africa.semicolon.parceldelivery.controllers;

import africa.semicolon.parceldelivery.services.ParcelService;
import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            parcelService.updateParcelStatus(parcel_id, status);
            var displayMessage = "Parcel status has been successfully updated!";
            return new ResponseEntity<>(displayMessage, HttpStatus.OK);
        } catch (ParcelIdException message) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parcel id number " + parcel_id + " does not exist!", message);
        }
    }
}
