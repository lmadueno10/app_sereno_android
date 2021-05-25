package com.appsereno.config;

import android.os.Environment;

/**
 * GlobalVaiables  is the final class where you define the global variables for the connection to the rest API.
 * <ul>
 *     <li>HOST_REST_API</li>
 *     <li>PORT_NUMBER</li>
 *     <li>PATH_BASE</li>
 * </ul>
 */
public final class GlobalVariables {
    //public static final String HOST_REST_API="https://app-sereno.herokuapp.com";
    public static final String HOST_REST_API="http://192.168.8.131";
    //public static final int PORT_NUMBER=88;
    public static final int PORT_NUMBER=3000;
    public static final String PATH_BASE="api";

    /**
     * This method always returns a string from the URI of the rest API.
     * @return {@link String}   the URI of rest API
     */
    public static String getBaseUri(){
        return HOST_REST_API+":"+(PORT_NUMBER!=80?PORT_NUMBER:"")+"/"+PATH_BASE+"/";
    }
    public static String getSocketUri(){
        return HOST_REST_API+":"+(PORT_NUMBER!=80?PORT_NUMBER:"");
    }
    public static final String ruta_evidencia= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/evidencia/";
}
