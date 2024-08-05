package com.portalasig.ms.commons.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.portalasig.ms.commons.persistence.CodeToEnumMapper;
import com.portalasig.ms.commons.persistence.Codeable;

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

    @JsonCreator
    public static Authority fromCode(String code) {
        return CODE_TO_ENUM_MAPPER.fromCode(code).get();
    }

    @JsonValue
    @Override
    public String getCode() {
        return code;
    }
}
