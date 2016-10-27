package com.mch.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @version 2.0 
 * @author Catalina Charry, Alberto Dominguez
 */
public class DateManager {
  private Calendar cal;
  private static String months[] = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre" };

  public static DateManager getInstance() {
    return new DateManager();
  }

  public Calendar validate(String dateStr, String sep) throws ParseException {
    SimpleDateFormat sdf;

    sdf = new SimpleDateFormat("dd" + sep + "MM" + sep + "yyyy");
    cal = Calendar.getInstance();
    cal.setTime(sdf.parse(dateStr));

    return cal;
  }

  public static String getMonthName(String dateStr, String sep) {
    String month = "Undefined";

    try {
      month = DateManager.months[getMonth(dateStr, sep) - 1];
    }
    catch (IndexOutOfBoundsException iobx) {
    }

    return month;
  }
  public static String getMonthName(String month2) {
    String month = "Undefined";

    try {
      month = DateManager.months[(Integer.parseInt(month2)) - 1];
    }
    catch (IndexOutOfBoundsException iobx) {
    }

    return month;
  }

  public static int getDay(String dateStr, String sep) {
    DateManager dm;
    int d = -1;

    try {
      dm = getInstance();
      dm.validate(dateStr, sep);
      d = dm.cal.get(Calendar.DAY_OF_MONTH);
    }
    catch (ParseException e) {
    }

    return d;
  }

  public static int getMonth(String dateStr, String sep) {
    DateManager dm;
    int y = -1;

    try {
      dm = getInstance();
      dm.validate(dateStr, sep);
      y = dm.cal.get(Calendar.MONTH) + 1;
    }
    catch (ParseException e) {
    }

    return y;
  }

  public static int getYear(String dateStr, String sep) {
    DateManager dm;
    int m = -1;

    try {
      dm = getInstance();
      dm.validate(dateStr, sep);
      m = dm.cal.get(Calendar.YEAR);
    }
    catch (ParseException e) {
    }

    return m;
  }
}
