package africa.semicolon.parceldelivery.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getType();
    }

    @Override
    public Role convertToEntityAttribute(String type) {
        if (type == null) {
            return null;
        }
        return Role.decode(type);
    }
}
