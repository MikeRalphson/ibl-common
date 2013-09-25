package bbc.iplayer.common.definition;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.regex.Pattern;

public abstract class ValidatingIdentifiable extends DataId {


    public ValidatingIdentifiable(String pid) {
        super(pid);
        if (pid == null || !getValidationPattern().matcher(pid).matches()) {
            throw new IllegalArgumentException("Argument does not match " + getValidationPattern().pattern());
        }

    }

    protected abstract Pattern getValidationPattern();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
