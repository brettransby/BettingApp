package com.betting.brettransby.bettingapp.NetworkCalls;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by brettransby on 10/26/16.
 */
public class NetworkCall {


    private static String HOST_URL = "https://jsonodds.com/api/";
    private static String API_KEY_KEY = "JsonOdds-API-Key";
    private static String API_KEY = "c369bd5e-9b8b-11e6-8199-067e79ca11d3";


    public static void makeObject(final Context context, final String urlAddOn, final FutureCallback<JsonObject> callback ){



        Ion.with( context )
                .load( HOST_URL+ urlAddOn )
                .addHeader( API_KEY_KEY, API_KEY )

                .asJsonObject()
                .setCallback( callback );

    }



    public static void makeArray( final Context context, final String urlAddOn, final FutureCallback<JsonArray> callback ){


        Ion.with( context )
                .load( HOST_URL+ urlAddOn )
                .addHeader( API_KEY_KEY, API_KEY )
                .asJsonArray()
                .setCallback( callback );
    }

}
