package bbc.iplayer.ibl.model.schema.adapters;

import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PeriodAdapter extends XmlAdapter<String, Period> {

    private PeriodFormatter periodFormatter = ISOPeriodFormat.standard();

    @Override
    public Period unmarshal(String s) {
        return periodFormatter.parsePeriod(s);
    }

    @Override
    public String marshal(Period period) {
        Period iblPeriod = period.normalizedStandard(PeriodType.forFields(iblDurationFields()));
        return periodFormatter.print(iblPeriod);
    }

    public static Period unmarshall(String period) {
        return new PeriodAdapter().unmarshal(period);
    }

    public static String marshall(Period period) {
        return new PeriodAdapter().marshal(period);
    }

    /**
     * Custom fields shown for iBL durations (i.e. no milliseconds)
     */
    private DurationFieldType[] iblDurationFields() {
        DurationFieldType[] fields = {
                DurationFieldType.years(),
                DurationFieldType.months(),
                DurationFieldType.weeks(),
                DurationFieldType.days(),
                DurationFieldType.hours(),
                DurationFieldType.minutes(),
                DurationFieldType.seconds()
        };

        return fields;
    }
}
