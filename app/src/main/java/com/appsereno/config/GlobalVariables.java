package com.appsereno.config;

public class GlobalVariables {
    public static final String HOST_REST_API="http://127.0.0.1";
    public static final int PORT_NUMBER=3000;
    public static final String PATH_BASE="api";
    public static String getBaseUri(){
        return HOST_REST_API+":"+PORT_NUMBER+"/"+PATH_BASE+"/";
    }
}
