package utils;

import java.time.Year;
import java.util.Iterator;
import java.util.Objects;

public class YearInterval implements Iterable<Year> {
    private Year start;
    private Year end;

    public YearInterval(Year start, Year end) {
        this.start = start;
        this.end = end;
    }

    public YearInterval(Year start) {
        this(start,start);
    }
    public boolean contains(Year y) {
        return start.compareTo(y) <= 0 && y.compareTo(end) <= 0;
    }

    public boolean contains(YearInterval interval) {
        return start.compareTo(interval.start) <= 0 && interval.end.compareTo(end) <= 0;
    }

    public boolean before(Year year){
        return end.compareTo(year) <=0;
    }

    public boolean before(YearInterval interval){
        return end.compareTo(interval.start) <=0;
    }

    public boolean after(Year year){
        return year.compareTo(start) <= 0;
    }

    public Iterator<Year> iterator() {
        return new Iterator<Year>() {
            Year current = start.minusYears(1);

            @Override
            public boolean hasNext() {
                return current.plusYears(1).compareTo(end) <= 0;
            }

            @Override
            public Year next() {
                return current = current.plusYears(1);
            }
        };

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearInterval)) return false;
        YearInterval years = (YearInterval) o;
        return Objects.equals(start, years.start) &&
                Objects.equals(end, years.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        if (start.equals(end)) return start.toString();
        return String.format("(%d,%d)",start.getValue(),end.getValue());
    }

}