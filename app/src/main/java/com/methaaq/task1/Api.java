package com.methaaq.task1;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hind on 11/19/2017.
 */

public interface Api {

    @POST("api/{email}/{password}/{utype}/{id}")
    Call<Login> registration( @Path("email") String email,@Path("password") String password, @Path("utype") String utype, @Path("id") String token);

}
