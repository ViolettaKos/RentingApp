package com.example.rentingapp.web.command.constants;

public interface Path {

    String CONTROLLER_PAGE = "controller";

    String CURRENT_PATH = "currentPath";

    ////////////////////           BASE           ////////////////////
    String MAIN_PAGE = "mainPage.jsp";
    String ERROR_PAGE = "errorPage.jsp";
    String SIGN_UP_PAGE = "signUp.jsp";
    String LOGIN_PAGE = "login.jsp";
    String CONTACTS_PAGE = "contactsPage.jsp";
    String RESTORE_PAGE = "restorePass.jsp";

    ////////////////////           CARS           ////////////////////
    String BOOK_PAGE = "bookPage.jsp";
    String PRODUCT_PAGE = "productPage.jsp";

    ////////////////////           ORDER           ////////////////////
    String ORDER_PAGE = "orderPage.jsp";
    String FAILURE_PAGE = "failurePay.jsp";
    String SUCCESS_PAGE = "successfulPay.jsp";

    ////////////////////           PROFILE           ////////////////////
    String PROFILE_PAGE = "profilePage.jsp";
    String EDIT_PAGE = "editProfile.jsp";
    String MY_ORDERS = "myOrders.jsp";
    String PAYMENT_PAGE = "paymentPage.jsp";

    ////////////////////           ADMIN           ////////////////////
    String ADMIN_CARS_PAGE = "adminCars.jsp";
    String ADMIN_USERS_PAGE = "adminUsers.jsp";
    String ADMIN_MNG_PAGE = "adminManagers.jsp";
    String ADD_CAR_PAGE = "addCar.jsp";
    String ADD_MNG_PAGE = "addManager.jsp";
    String EDIT_CAR_PAGE = "editCar.jsp";
    String EDIT_MANAGER_PAGE = "editManager.jsp";

    ////////////////////           MANAGER          ////////////////////
    String MNG_ORDERS_PAGE = "managerOrders.jsp";
    String ORDER_INFO_PAGE = "viewOrderInfoManager.jsp";
    String REG_RETURN_PAGE = "registerReturn.jsp";
    String REJECT_ORDER_PAGE = "rejectOrder.jsp";


    ////////////////////           IMG UPLOAD           ////////////////////
    String BRAND_IMG = "c:\\Users\\suraw\\IdeaProjects\\RentingApp\\src\\main\\webapp\\img\\brands\\";
    String CAR_IMG = "c:\\Users\\suraw\\IdeaProjects\\RentingApp\\src\\main\\webapp\\img\\cars\\";

}
