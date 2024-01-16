package org.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewFunctions {
    private Review review;
    private Retrofit retrofit;
    private ReviewsApi reviewsApi;
    private Call<List<Review>> call;

    //Constructor
    public ReviewFunctions(Review review, Retrofit retrofit, ReviewsApi reviewsApi, Call<List<Review>> call) {
        this.review = review;
        this.retrofit = retrofit;
        this.reviewsApi = reviewsApi;
        this.call = call;
    }

    //Methods!
    //Lists All Reviews
    public void listAll() {
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

    //Lists a specific review
    public void listByID(int id) {
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

    //Call Review through API
    public List<String> listIDFromApi(int id) {
        List<String> reviewList = new ArrayList<>();
        try {
            Call<Review> call = reviewsApi.getReviewById(id);
            Response<Review> response = call.execute();

            if (response.isSuccessful()) {
                Review review = response.body();
                System.out.println("Review ID: " + review.getId());
                System.out.println("Author: " + review.getAuthor());
                System.out.println("Comment: " + review.getComment());
                System.out.println("Score: " + review.getScore());
                System.out.println("Date: " + review.getDate());

                reviewList.add(review.getAuthor());
                reviewList.add(String.valueOf(review.getScore()));
                reviewList.add(review.getComment());
                reviewList.add(review.getDate());
                return reviewList;
            } else {
                System.out.println("Error: " + response.code());
                reviewList.add("Error");
                return reviewList;
            }
        } catch (IOException e) {
            e.printStackTrace();
            reviewList.add("Error");
            return reviewList;
        }
    }

    //Post Review
    public void postReview(String author, String comment, int score) {
        Review newReview = new Review();
//        newReview.setId(6);
        newReview.setAuthor(author);
        newReview.setComment(comment);
        newReview.setScore(score);

        // Get the current time
        OffsetDateTime now = OffsetDateTime.now();
        // Define the formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // Format the OffsetDateTime to a string
        String formattedDate = now.format(formatter);
        newReview.setDate(formattedDate);


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

    //Delete Review By ID
    public boolean deleteReviewById(int id) {
        // Assuming 'id' is the path parameter
        Call<Void> call = reviewsApi.deleteReviewById(id);

        try {
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                return true;
            } else {
                System.out.println("Error: " + response.code());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Update Review Saga
    public boolean updateReview(int id, String author, String comment, int score) {
        //Needed to send the ID through JSON aswell
        //ID needs to eb the same
        review.setId(id);
        review.setAuthor(author);
        review.setComment(comment);
        review.setScore(score);

        // Get the current time
        OffsetDateTime now = OffsetDateTime.now();
        // Define the formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // Format the OffsetDateTime to a string
        String formattedDate = now.format(formatter);
        review.setDate(formattedDate);

        Call<Void> call = reviewsApi.updateReviewById(id, review);

        try {
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                System.out.println("Review updated successfully!");
                return true;
            } else {
                System.out.println("Error: " + response.code());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double averageScore() {
        try {
            // Execute the call and handle the response
            Response<List<Review>> response = call.clone().execute();

            if (response.isSuccessful()) {
                List<Review> reviews = response.body();

                double average = 0;
                double i = 0;
                // Process the list of reviews
                for (Review review : reviews) {
                    average += review.getScore();
                    i++;
                }
                System.out.println();
                return average / i;
            } else {
                // Handle error response
                System.out.println("Error: " + response.code());
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Review> getReviews() {
        try {
            // Execute the call and handle the response
            Response<List<Review>> response = call.clone().execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Handle error response (you might want to throw an exception or log the error)
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Handle the case where fetching reviews fails
    }
}

