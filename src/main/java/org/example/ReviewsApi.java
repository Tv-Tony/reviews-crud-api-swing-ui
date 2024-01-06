package org.example;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReviewsApi {
    @GET("/api/review")
    Call<List<Review>> getReviews();

    @POST("/api/review")
    Call<Void> postReview(@Body Review review);

}
