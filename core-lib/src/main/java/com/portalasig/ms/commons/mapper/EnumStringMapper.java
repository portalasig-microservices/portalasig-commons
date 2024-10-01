package com.portalasig.ms.commons.mapper;

import com.portalasig.ms.commons.persistence.Codeable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnumStringMapper {

    @SuppressWarnings({"unchecked", "PMD.AvoidCatchingGenericException"})
    static <T extends Enum<T> & Codeable<String>> T fromStringToEnum(String string, Class<T> enumClass) {
        try {
            if (string != null && enumClass != null) {
                // Use reflection to invoke the fromCode method
                return (T) enumClass.getMethod("fromCode", String.class).invoke(null, string);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error calling fromCode on enum " + enumClass.getSimpleName(), e);
        }
        return null;
    }

    @Named("fromEnumToString")
    static <T extends Enum<T> & Codeable<String>> String fromEnumToString(T enumClass) {
        return enumClass != null ? enumClass.getCode() : null;
    }
}
