/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author E_Villodas
 */
public class DateManagerUtil {

    public static String printCalendarDate(Calendar c) {
        if (c == null) {
            return "-";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(c.getTime());
    }
}
