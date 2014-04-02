package uk.co.bbc.iplayer.common.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import uk.co.bbc.iplayer.common.definition.Pid;

import javax.annotation.Nullable;
import java.util.List;

public class FromStringToPid implements Function<String, Pid> {

    @Nullable
    @Override
    public Pid apply(@Nullable String pid) {
        return new Pid(pid);
    }

    public static List<Pid> fromList(List<String> pids) {
        return Lists.transform(pids, new FromStringToPid());
    }
}
