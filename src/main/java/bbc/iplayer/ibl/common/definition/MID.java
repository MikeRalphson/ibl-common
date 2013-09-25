package bbc.iplayer.ibl.common.definition;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class MID extends ValidatingIdentifiable {
    // Allow empty mids
    private static Pattern pattern = Pattern.compile("^[a-z0-9_]*$");

    public MID(@Nullable String pid) {
        super(pid == null ? "" : pid);
    }

    @Override
    protected Pattern getValidationPattern() {
        return pattern;
    }
}