package app.main.maps;

import java.util.Objects;

public enum MapType {
    EQUATOR, TOXIC;

    public static MapType fromString(String variant) {
        if (Objects.equals(variant, "equator")) {
            return MapType.EQUATOR;
        } else return MapType.TOXIC;
    }
}
