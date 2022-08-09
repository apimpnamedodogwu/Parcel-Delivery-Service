package africa.semicolon.parceldelivery.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ParcelDeliveryStatusConverter implements AttributeConverter<ParcelDeliveryStatus, String> {
    @Override
    public String convertToDatabaseColumn(ParcelDeliveryStatus parcelDeliveryStatus) {
        if (parcelDeliveryStatus == null) {
            return null;
        }
        return parcelDeliveryStatus.getStatus();
    }

    @Override
    public ParcelDeliveryStatus convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }
        return ParcelDeliveryStatus.decode(status);
    }
}
