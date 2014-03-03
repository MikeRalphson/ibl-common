package uk.co.bbc.iplayer.common.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Pid extends ValidatingIdentifiable implements Identifiable {
    // match string containing aplha numeric chars (including '-' & '_'), with a max length of 20
    private static Pattern pattern = Pattern.compile("^[a-zA-Z0-9-_]{1,20}$");

    public Pid(String pid) {
        super(pid);
    }

    @Override
    protected Pattern getValidationPattern() {
        return pattern;
    }

    public static List<String> fromPids(List<Pid> pids) {
        List<String> asStrings = new ArrayList<String>();
        for (Pid pid : pids) {
            asStrings.add(pid.getId());
        }
        return asStrings;
    }
}
