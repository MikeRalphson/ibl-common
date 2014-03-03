package uk.co.bbc.iplayer.common.definition;

import java.util.regex.Pattern;

public abstract class ValidatingIdentifiable extends DataId {
    protected abstract Pattern getValidationPattern();

    public ValidatingIdentifiable(String pid) {
        super(pid);
        if (pid == null || !getValidationPattern().matcher(pid).matches()) {
            throw new IllegalArgumentException("Argument does not match " + getValidationPattern().pattern());
        }
    }
}
