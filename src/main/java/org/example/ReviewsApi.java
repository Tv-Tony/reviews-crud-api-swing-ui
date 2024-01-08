package org.example;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;
public interface ReviewsApi {
    @GET("/api/review")
    Call<List<Review>> getReviews();

    @GET("/api/review/{id}")
    Call<Review> getReviewById(@Path("id") int id);

    @POST("/api/review")
    Call<Void> postReview(@Body Review review);

//    @PUT("/api/review/{id}")
//    Call<Void> updateReviewById(@Path("id") int id, @Body Review updatedReview);

    @PUT("/api/review/{id}")
    Call<Void> updateReviewById(@Path("id") int id, @Body Review review);

}
