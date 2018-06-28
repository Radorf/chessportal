/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 *
 * @author E_Villodas
 */
public class MenuBarLayout {

    @Property
    @Parameter(required = true, defaultPrefix = "message")
    private String title;
}
