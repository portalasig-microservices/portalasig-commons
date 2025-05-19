package com.portalasig.ms.commons.mapper;

import com.portalasig.ms.commons.persistence.Codeable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

/**
 * Mapper for converting between enums implementing {@link Codeable} and their string codes.
 * <p>
 * This mapper:
 * <ul>
 *   <li>Converts a string to an enum using the enum's static fromCode method.</li>
 *   <li>Converts an enum to its code string using {@link Codeable#getCode()}.</li>
 * </ul>
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnumStringMapper {

    /**
     * Converts a string to an enum instance using the enum's static fromCode method.
     *
     * @param string    the code string
     * @param enumClass the enum class
     * @param <T>       the enum type
     * @return the enum instance, or null if input is null
     * @throws IllegalArgumentException if fromCode invocation fails
     */
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

    /**
     * Converts an enum instance to its code string.
     *
     * @param enumClass the enum instance
     * @param <T>       the enum type
     * @return the code string, or null if input is null
     */
    @Named("fromEnumToString")
    static <T extends Enum<T> & Codeable<String>> String fromEnumToString(T enumClass) {
        return enumClass != null ? enumClass.getCode() : null;
    }
}