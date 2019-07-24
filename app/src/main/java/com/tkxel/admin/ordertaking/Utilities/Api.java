package com.tkxel.admin.ordertaking.Utilities;

import com.tkxel.admin.ordertaking.ModelClass.Customer;
import com.tkxel.admin.ordertaking.ModelClass.Merchandiser;
import com.tkxel.admin.ordertaking.ModelClass.MerchandiserVisit;
import com.tkxel.admin.ordertaking.ModelClass.ProductAdvertisement;
import com.tkxel.admin.ordertaking.ModelClass.ProductAvailability;
import com.tkxel.admin.ordertaking.ModelClass.ProductPlacementVisibility;
import com.tkxel.admin.ordertaking.ModelClass.SellingPattern;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Belal on 10/2/2017.
 */

public interface Api {

    String BASE_URL = "http://192.168.18.129:8082/";

//    String BASE_URL = "http://45.76.247.39/";

    //http://192.168.8.129/api/Merchandiser/GetMerchandisers

    @GET("api/Customer/GetCustomers")
    Call<Object> GetCustomers();


    @GET("api/Customer/GetCustomerByCityArea/{city}/{area}")
    Call<Object> GetCustomerByCityArea(@Path("city") String city,@Path("area") String area);

    @GET("api/Merchandiser/GetMerchandisers")
    Call<Object> GetMerchandisers();

    @GET("api/Merchandiser/GetMerchandisersByUserID/{userid}")
    Call<Object> GetMerchandisersByUserID(@Path("userid") int userid);

    @GET("api/Product/GetProductsLevel1")
    Call<Object> GetProductsLevel1();

    @GET("api/Product/GetProducts")
    Call<Object> GetProducts();

    @GET("/api/Customer/GetShops")
    Call<Object> GetShops();

    @GET("api/Customer/GetShopsCity")
    Call<Object> GetShopsCity();

    @GET("/api/Customer/GetAreaByCity/{city}")
    Call<Object> GetShopsByArea(@Path("city") String city);

    @POST("api/Merchandiser/PostMerchandiser")
    Call<Object> PostMerchandiser(@Body Merchandiser merchandiser);

    @POST("api/Customer/PostCustomer")
    Call<Object> PostCustomer(@Body Customer customer);


    @POST("api/Product/PostProductAvailability")
    Call<Object> PostProductAvailability(@Body List<ProductAvailability> productAvailability);

    @POST("api/Product/PostProductAdvertisement")
    Call<Object> PostProductAdvertisement(@Body List<ProductAdvertisement> productAdvertisement);


    @POST("api/Product/PostSellingPattern")
    Call<Object> PostSellingPattern(@Body SellingPattern sellingpattern);

    @GET("api/Users/GetUserIDByEmail/{email}")
    Call<Object> GetUserIDByEmail(@Path("email") String email);


    @POST("api/Product/PostProductPlacementVisibility")
    Call<Object> PostProductPlacementVisibility(@Body List<ProductPlacementVisibility>  productPlacementVisibility);

    @POST("api/Merchandiser/PostMerchandiserVisit")
    Call<Object> PostMerchandiserVisit(@Body MerchandiserVisit merchandiserVisit);


//    @PUT("api/Customer/UpdateCustomer")
//    Call<Object> UpdateCustomer(@Body Customer customer);
}
