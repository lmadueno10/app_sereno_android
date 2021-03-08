package com.appsereno.config;

public class GlobalVariables {
    public static final String HOST_REST_API="http://192.168.8.129";
    public static final int PORT_NUMBER=3000;
    public static final String PATH_BASE="api";
    public static String getBaseUri(){
        return HOST_REST_API+":"+PORT_NUMBER+"/"+PATH_BASE+"/";
    }
}
