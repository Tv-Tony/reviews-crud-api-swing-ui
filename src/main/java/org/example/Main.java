package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static org.example.ApiUtils.retrofit;
import static org.example.ApiUtils.reviewsApi;
import static org.example.ApiUtils.call;

public class Main extends JFrame {
    private JList<Object> reviews;
    private JButton createReviewButton;
    private JButton updateReviewByIDButton;
    private JTextField createAuthor;
    private JTextArea createReview;
    private JTextField createScore;
    private JTextField idUpdateSearch;
    private JTextField updateAuthor;
    private JTextField updateScore;
    private JTextArea updateReview;
    private JButton deleteReviewButton;
    private JTextField deleteByID;
    private JPanel MainPanel;
    private JButton searchReviewByIDButton;
    private JTextField idFindSearch;
    private JTextField authorFind;
    private JTextField scoreFind;
    private JTextArea reviewFind;
    private JTextField scoreDisplay;
    private JTextField dateFind;

    public ReviewFunctions reviewFunctions = new ReviewFunctions(new Review(), retrofit, reviewsApi, call);

    public Main() {
        setContentPane(new JScrollPane(MainPanel));
        setTitle("Simple Gui App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        JScrollPane scrollPane = new JScrollPane();

        scoreDisplay.setText(String.valueOf(reviewFunctions.averageScore()));

        DefaultListModel<Object> listModel = new DefaultListModel<>();
        reviews.setModel(listModel);

        createReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newAuthor = createAuthor.getText();
                String newScore = createScore.getText();
                String newReview = createReview.getText();
                int newIntScore = 0;
                try {
                    newIntScore = Integer.parseInt(newScore);
                    if (newIntScore >= 0 && newIntScore <= 5){
                        reviewFunctions.postReview(newAuthor, newReview, newIntScore);
                        updateReviewList();
                        createAuthor.setText("");
                        createScore.setText("");
                        createReview.setText("");
                    }
                    else {
                        createScore.setText("Please select an int value between 0-5");
                    }
                } catch (NumberFormatException k) {
                    createScore.setText("Please select an int value between 0-5");
                }


            }
        });

        updateReviewList(); // Initial update to display existing reviews

        searchReviewByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idToSearch = Integer.parseInt(idFindSearch.getText());
                reviewFunctions.listByID(idToSearch);
            }
        });

        deleteReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteID = deleteByID.getText();
                int deleteIntID = 0;
                try {
                    deleteIntID = Integer.parseInt(deleteID);

                        if (reviewFunctions.deleteReviewById(deleteIntID) == true){
                            deleteByID.setText("Completed");
                            reviewFunctions.deleteReviewById(deleteIntID);
                        }
                        else {
                            deleteByID.setText("Operation Failed");
                        }
                } catch (NumberFormatException k) {
//                    deleteByID = "Error ";
                }
                updateReviewList();
            }
        });
        updateReviewByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updateID = idUpdateSearch.getText();
                String updateNewAuthor = updateAuthor.getText();
                String updateNewScore = updateScore.getText();
                String updateNewComment = updateReview.getText();

                int updateIntID = 0;
                int updateNewIntScore = 0;

                try {
                    updateIntID = Integer.parseInt(updateID);
                    updateNewIntScore = Integer.parseInt(updateNewScore);

                    if (updateNewIntScore < 0 || updateNewIntScore > 5) {
                        updateScore.setText("Please select a score between 0-5");
                        return; // Exit the method if score is invalid
                    }

                    boolean updateResult = reviewFunctions.updateReview(updateIntID, updateNewAuthor, updateNewComment, updateNewIntScore);

                    if (!updateResult) {
                        idUpdateSearch.setText("Invalid Review ID or fill all fields");
                    } else {
                        // Clear fields and provide feedback
                        idUpdateSearch.setText("Completed");
                        updateAuthor.setText("");
                        updateScore.setText("");
                        updateReview.setText("");
                        updateReviewList();
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case where ID or score is not a valid integer
                    idUpdateSearch.setText("Invalid ID or score");
                }
            }
        });
        searchReviewByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFindSearch.getText();
                int idInt = 0;
                try {
                    idInt = Integer.parseInt(id);

                    List<String> searchReviewList = reviewFunctions.listIDFromApi(idInt);

                    // Check if the result contains an "Error"
                    if (searchReviewList.contains("Error")) {
                        idFindSearch.setText("Invalid ID");
                    } else {
                        // Extract data from the list and update text fields
                        authorFind.setText(searchReviewList.get(0));
                        scoreFind.setText(searchReviewList.get(1));
                        reviewFind.setText(searchReviewList.get(2));
                        dateFind.setText(searchReviewList.get(3));
                    }
                } catch (NumberFormatException k) {
                    idFindSearch.setText("Invalid ID");
                }
            }
        });
    }

    private void updateReviewList() {
        // Fetch the latest reviews from your API or data source
        List<Review> reviewList = reviewFunctions.getReviews();

        // Clear the existing items in the JList model
        DefaultListModel<Object> listModel = (DefaultListModel<Object>) reviews.getModel();
        listModel.clear();

        // Add the reviews to the JList model with multiline rendering
        for (Review review : reviewList) {
            listModel.addElement("Review ID: " + review.getId());
            listModel.addElement("Author: " + review.getAuthor());
            listModel.addElement("Score: " + review.getScore());
            listModel.addElement("Comment: " +review.getComment());
            listModel.addElement("Date: " + review.getDate());
            listModel.addElement("----------------------------------------------------------");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
