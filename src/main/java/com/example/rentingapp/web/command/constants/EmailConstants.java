package com.example.rentingapp.web.command.constants;

public interface EmailConstants {
    String TOPIC_REGISTER="Successful registration!";
    String TOPIC_DAMAGE ="Your car was damaged!";
    String TOPIC_RETURN_CAR="Thank you for choosing us!";
    String TOPIC_ORDER="Order confirmation";
    String TOPIC_REJECTED="Your order was rejected!";
    String TOPIC_BLOCKED="Your account was blocked!";
    String TOPIC_UNBLOCKED="Your account was unblocked!";
    String TOPIC_PAY="Successful payment!";

    String SIGNATURE = "Best regards,<br>Car Rent team";
    String HELLO = "Hello %s,<br>";
    String INPUT = "%s";
    String SPACE = "<br><br>";
    String HREF = "<a href=";
    String MARK="?";
    String EQUAL="=";

    String MESSAGE_REGISTER = HELLO +
            "Thank you for choosing us!<br>" +
            "Welcome to Car Rent website. When you log into your account, the following features will be available to you:<br>" +
            "– Place orders<br>" +
            "– View order history<br>" +
            "– Browse cars for rent<br>" +
            "– Change account information" +
            SPACE+
            "Check " + HREF + "%2$s" + ">our cars</a>, " +
            " right now and enjoy your driving immediately!" +
            SPACE +
            SIGNATURE;
    String MESSAGE_DAMAGE = HELLO + "We are sorry to inform you that your car was damaged during your last trip!"+
            SPACE+"We ask you to pay the invoice as soon as possible in order to avoid problems."+
            SPACE+"Amount of money to pay: "+INPUT+SPACE+SIGNATURE;
    String MESSAGE_RETURN_CAR = HELLO +
            "Thank you for using our service! We hope you had a great time spending with our company."+SPACE+
            "We received the car and hope to help you again!"+
            SPACE+SIGNATURE;
    String MESSAGE_ORDER = HELLO +
            "Thank you for your order! We will contact you as soon as possible to clarify all the details."+SPACE+
            "Please feel free to contact us if you have any questions."+
            SPACE+SIGNATURE;
    String MESSAGE_REJECTED = HELLO +
            "Thank you for your recent order. Unfortunately we are unable to process your order and it has been cancelled, " +
            "due to this reason:<br>"+INPUT+SPACE+
            SIGNATURE;
    String MESSAGE_BLOCKED = HELLO +
            "Unfortunately your account was blocked and you can not make any orders at this time."+SPACE+
            "Please be free to contact us if you have any questions."+
            SPACE+SIGNATURE;
    String MESSAGE_UNBLOCKED = HELLO +
            "We are happy to inform you that your account was unblocked and you can make orders again."+
            SPACE+SIGNATURE;
    String MESSAGE_PAYMENT = HELLO +
            "Your Car Rent transaction was successful. Thank you!"+
            SPACE+SIGNATURE;
}
