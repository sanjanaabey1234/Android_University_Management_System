package com.example.loginsignup;

public class Course {
    private String courseName;
    private String courseFee;
    private String startingDate;
    private String registrationCloseDate;
    private String selectedBranch;
    private String selectedDuration;

    // Constructor
    public Course() {
        // Default constructor
    }

    // Getters and setters for all fields
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(String courseFee) {
        this.courseFee = courseFee;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(String registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

    public String getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(String selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    public String getSelectedDuration() {
        return selectedDuration;
    }

    public void setSelectedDuration(String selectedDuration) {
        this.selectedDuration = selectedDuration;
    }
}
