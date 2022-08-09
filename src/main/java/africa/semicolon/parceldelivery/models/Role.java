package africa.semicolon.parceldelivery.models;

import africa.semicolon.parceldelivery.services.parcelExceptions.RoleException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Role {
    TYPE_1("USER"),
    TYPE_2("ADMIN"),
    TYPE_3("STAFF");

    private String type;

    Role(String type) {
        this.type = type;
    }

    @JsonCreator
    public static Role decode(final String type) {
        return Stream.of(Role.values()).filter(targetEnum -> targetEnum.type.equals(type)).findFirst().orElseThrow(() -> new RoleException("Role type is invalid!"));
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
