package com.portalasig.ms.commons.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.portalasig.ms.commons.persistence.CodeToEnumMapper;
import com.portalasig.ms.commons.persistence.Codeable;

/**
 * Enumeration representing the authorities/roles available in the system.
 */
public enum Authority implements Codeable<String> {
    USER("USER"),
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR");

    private static final CodeToEnumMapper<String, Authority> CODE_TO_ENUM_MAPPER =
            new CodeToEnumMapper<>(Authority.class);

    final String code;

    Authority(String code) {
        this.code = code;
    }

    /**
     * Resolves an {@link Authority} enum from a given string code.
     *
     * @param code the string representation of the authority
     * @return the matching {@link Authority}
     */
    @JsonCreator
    public static Authority fromCode(String code) {
        return CODE_TO_ENUM_MAPPER.fromCode(code).get();
    }

    /**
     * Returns the code representation of this authority.
     *
     * @return the code as a string
     */
    @JsonValue
    @Override
    public String getCode() {
        return code;
    }
}
