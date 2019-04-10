package Domain;

import java.util.Calendar;
import java.util.GregorianCalendar;


// questo tester è un area atta alla sperimentazione di codice non strettamente attinente al progetto

public class GarbageTesterMain {
    public static void main(String[] args) {
        Calendar g = GregorianCalendar.getInstance();
        System.out.println(g.get(Calendar.MONTH));
        GregorianCalendar gregorianCalendar00 = new GregorianCalendar(2019, 4,10,17, 00);
        System.out.println((g.get(Calendar.HOUR_OF_DAY))==(gregorianCalendar00.get(GregorianCalendar.HOUR_OF_DAY)));
        System.out.println((g.get(Calendar.YEAR))==(gregorianCalendar00.get(GregorianCalendar.YEAR)));
        System.out.println((g.get(Calendar.MONTH)) == (gregorianCalendar00.get(GregorianCalendar.MONTH)));
        System.out.println((g.get(Calendar.DAY_OF_MONTH)) == (gregorianCalendar00.get(GregorianCalendar.DAY_OF_MONTH)));
    }
}