package com.example.rentingapp.dao.DAOImpl.constants;

public interface Fields {

    //////////////      DB FIELDS       ///////////////
    String LIMIT="LIMIT ?, ?";
    String NUM_OF_REC="numberOfRecords";
    String ORDER_BY="ORDER BY ";
    String ASC=" ASC";
    String SORT="sort";
    String CURR_PAGE="currentPage";
    String REC_PER_PAGE="recordsPerPage";
    String NUM_OF_PAGES="noOfPages";


    //////////////      USER       ///////////////
    String FIRST_NAME="first_name";
    String LAST_NAME="last_name";
    String LOGIN="login";
    String EMAIL="email";
    String PASS="password";
    String TELEPHONE="telephone";
    String ROLE="role";
    String BLOCKED="blocked";
    String MONEY="money";
    String USER_ID="user_id";

    //////////////       CAR       ///////////////
    String NAME="name";
    String BRAND="brand";
    String QUALITY="quality_class";
    String PRICE="price";
    String CAR_ID="car_id";
    String AVAILABLE="isAvailable";

    //////////////       ORDER       ///////////////
    String AGE="age";
    String FROM="from";
    String TO="to";
    String OPTION="option";
    String ORDER_ID="order_id";


    //////////////       ORDER INFO      ///////////////
    String TOTAL_DAYS="total_days";
    String IS_PAYED="isPayed";
    String IS_REJECTED="isRejected";
    String TOTAL_PRICE="total_price";
    String REASON_REJECT="reason_for_reject";
}
