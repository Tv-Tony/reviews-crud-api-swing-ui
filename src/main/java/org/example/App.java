package org.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.List;

public class App 
{
    public static void main( String[] args ) {

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeDeserializer())
//                .create();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://utbreviewsapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeDeserializer())
                        .create()))
                .build();

        // Create ReviewsApi instance
        ReviewsApi reviewsApi = retrofit.create(ReviewsApi.class);

        // Make the API call
        Call<List<Review>> call = reviewsApi.getReviews();

        //Static method to post review
        postReview("Timmy Turner", "The restaurant has good food", 5);

        //Static Method to call All reviews
        allReviews(retrofit, reviewsApi, call);

        //Static Method to find a certain review based on ID
        System.out.println("This is the specific review you called");
        specificReviews(retrofit,reviewsApi,call,1);






    }

    static void postReview(String author, String comment, int score){
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://utbreviewsapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create ReviewsApi instance
        ReviewsApi reviewsApi = retrofit.create(ReviewsApi.class);
        Review newReview = new Review();
        newReview.setId(6);
        newReview.setAuthor(author);
        newReview.setComment(comment);
        newReview.setScore(score);
        newReview.setDate(OffsetDateTime.now());

        Call<Void> call = reviewsApi.postReview(newReview);
        try {
            // Execute the call and handle the response
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                System.out.println("Review posted successfully!");
            } else {
                // Handle error response
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void allReviews(Retrofit retrofit, ReviewsApi reviewsApi, Call<List<Review>> call){
        try {
            // Execute the call and handle the response
            Response<List<Review>> response = call.clone().execute();

            if (response.isSuccessful()) {
                List<Review> reviews = response.body();

                // Process the list of reviews
                for (Review review : reviews) {
                    System.out.println("Review ID: " + review.getId());
                    System.out.println("Author: " + review.getAuthor());
                    System.out.println("Comment: " + review.getComment());
                    System.out.println("Score: " + review.getScore());
                    System.out.println("Date: " + review.getDate());
                    System.out.println("--------------");
                }
            } else {
                // Handle error response
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void specificReviews(Retrofit retrofit, ReviewsApi reviewsApi, Call<List<Review>> call, int id){
        try {
            // Execute the call and handle the response
            Response<List<Review>> response = call.clone().execute();

            if (response.isSuccessful()) {
                List<Review> reviews = response.body();

                // Process the list of reviews
                for (Review review : reviews) {
                    if (review.getId() == id) {
                    System.out.println("Review ID: " + review.getId());
                    System.out.println("Author: " + review.getAuthor());
                    System.out.println("Comment: " + review.getComment());
                    System.out.println("Score: " + review.getScore());
                    System.out.println("Date: " + review.getDate());
                    System.out.println("--------------");
                    }
                }
            } else {
                // Handle error response
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}