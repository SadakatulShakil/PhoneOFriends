package com.example.phoneofriends.View.Model;

public class UserDetails {
    private String userId;
    private String userSchool;
    private String userCollege;
    private String userUniversity;
    private String userHomeArea;
    private String userHomeRoad;
    private String userHouseNo;
    private String userJobTitle;
    private String userOfficeName;
    private String userOfficeLocation;

    public UserDetails() {
    }

    public UserDetails(String userId, String userSchool, String userCollege,
                       String userUniversity, String userHomeArea, String userHomeRoad,
                       String userHouseNo, String userJobTitle, String userOfficeName, String userOfficeLocation) {

        this.userId = userId;
        this.userSchool = userSchool;
        this.userCollege = userCollege;
        this.userUniversity = userUniversity;
        this.userHomeArea = userHomeArea;
        this.userHomeRoad = userHomeRoad;
        this.userHouseNo = userHouseNo;
        this.userJobTitle = userJobTitle;
        this.userOfficeName = userOfficeName;
        this.userOfficeLocation = userOfficeLocation;
    }

    public UserDetails(String userSchool, String userCollege, String userUniversity,
                       String userHomeArea, String userHomeRoad, String userHouseNo,
                       String userJobTitle, String userOfficeName, String userOfficeLocation) {
        this.userSchool = userSchool;
        this.userCollege = userCollege;
        this.userUniversity = userUniversity;
        this.userHomeArea = userHomeArea;
        this.userHomeRoad = userHomeRoad;
        this.userHouseNo = userHouseNo;
        this.userJobTitle = userJobTitle;
        this.userOfficeName = userOfficeName;
        this.userOfficeLocation = userOfficeLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    public String getUserHomeArea() {
        return userHomeArea;
    }

    public void setUserHomeArea(String userHomeArea) {
        this.userHomeArea = userHomeArea;
    }

    public String getUserHomeRoad() {
        return userHomeRoad;
    }

    public void setUserHomeRoad(String userHomeRoad) {
        this.userHomeRoad = userHomeRoad;
    }

    public String getUserHouseNo() {
        return userHouseNo;
    }

    public void setUserHouseNo(String userHouseNo) {
        this.userHouseNo = userHouseNo;
    }

    public String getUserJobTitle() {
        return userJobTitle;
    }

    public void setUserJobTitle(String userJobTitle) {
        this.userJobTitle = userJobTitle;
    }

    public String getUserOfficeName() {
        return userOfficeName;
    }

    public void setUserOfficeName(String userOfficeName) {
        this.userOfficeName = userOfficeName;
    }

    public String getUserOfficeLocation() {
        return userOfficeLocation;
    }

    public void setUserOfficeLocation(String userOfficeLocation) {
        this.userOfficeLocation = userOfficeLocation;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId='" + userId + '\'' +
                ", userSchool='" + userSchool + '\'' +
                ", userCollege='" + userCollege + '\'' +
                ", userUniversity='" + userUniversity + '\'' +
                ", userHomeArea='" + userHomeArea + '\'' +
                ", userHomeRoad='" + userHomeRoad + '\'' +
                ", userHouseNo='" + userHouseNo + '\'' +
                ", userJobTitle='" + userJobTitle + '\'' +
                ", userOfficeName='" + userOfficeName + '\'' +
                ", userOfficeLocation='" + userOfficeLocation + '\'' +
                '}';
    }
}
