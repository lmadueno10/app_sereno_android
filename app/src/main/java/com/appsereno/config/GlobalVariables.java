package com.appsereno.config;

/**
 * GlobalVaiables  is the final class where you define the global variables for the connection to the rest API.
 * <ul>
 *     <li>HOST_REST_API</li>
 *     <li>PORT_NUMBER</li>
 *     <li>PATH_BASE</li>
 * </ul>
 */
public final class GlobalVariables {
    public static final String HOST_REST_API="http://192.168.8.129";
    public static final int PORT_NUMBER=3000;
    public static final String PATH_BASE="api";

    /**
     * This method always returns a string from the URI of the rest API.
     * @return {@link String}   the URI of rest API
     */
    public static String getBaseUri(){
        return HOST_REST_API+":"+PORT_NUMBER+"/"+PATH_BASE+"/";
    }
}
