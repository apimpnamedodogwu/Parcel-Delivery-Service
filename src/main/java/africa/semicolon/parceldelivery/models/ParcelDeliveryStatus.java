package africa.semicolon.parceldelivery.models;

import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelDeliveryStatusException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum ParcelDeliveryStatus {
    CODE_0("FAILED_TO_DELIVER"),
    CODE_1("PENDING"),
    CODE_2("IN_TRANSIT"),
    CODE_3("DELIVERED");

    private String status;

    ParcelDeliveryStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static ParcelDeliveryStatus decode(final String status) {
        return Stream.of(ParcelDeliveryStatus.values()).filter(targetEnum -> targetEnum.status.equals(status)).findFirst().orElseThrow(() -> new ParcelDeliveryStatusException(status));
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
