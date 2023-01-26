package com.example.rentingapp.web.command.constants;

public interface Path {

    String CONTROLLER_PAGE = "controller";

    String CURRENT_PATH = "currentPath";

    ////////////////////           BASE           ////////////////////
    String MAIN_PAGE = "mainPage.jsp";
    String ERROR_PAGE = "errorPage.jsp";
    String SIGN_UP_PAGE = "signUp.jsp";
    String LOGIN_PAGE = "login.jsp";

    ////////////////////           CARS           ////////////////////
    String BOOK_PAGE="bookPage.jsp";
    String PRODUCT_PAGE="productPage.jsp";

    ////////////////////           ORDER           ////////////////////
    String ORDER_PAGE="orderPage.jsp";
    String FAILURE_PAGE="failurePay.jsp";
    String SUCCESS_PAGE="successfulPay.jsp";

    ////////////////////           PROFILE           ////////////////////
    String PROFILE_PAGE = "profilePage.jsp";
    String EDIT_PAGE="editProfile.jsp";
    String MY_ORDERS="myOrders.jsp";
}
