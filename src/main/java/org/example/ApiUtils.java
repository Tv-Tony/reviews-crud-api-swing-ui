package org.example;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ApiUtils {
    public static final String BASE_URL = "https://utbreviewsapi.azurewebsites.net/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final ReviewsApi reviewsApi = retrofit.create(ReviewsApi.class);
    public static final Call<List<Review>> call = reviewsApi.getReviews();
    public static final Review review = new Review();
}
