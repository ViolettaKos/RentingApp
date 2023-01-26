<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title><fmt:message key="payment"/></title>
    <link rel="stylesheet" type="text/css" href="style/credit_card.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>

<!-- ==============================================
Credit Card Payment Section
=============================================== -->
<section class="credit-card">
    <div class="container">
        <div class="card-holder">
            <div class="card-box bg-news">
                <div class="row">
                    <div class="col-lg-6">
                        <div class="img-box">
                            <img src="img/pay.png" style="width: 400px"/>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <form name="payment" action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="pay">
                            <div class="card-details">
                                <h3 class="title"><fmt:message key="card.details"/></h3>
                                <div class="row">
                                    <div class="form-group col-sm-7">
                                        <div class="inner-addon right-addon">
                                            <label for="card-holder"><fmt:message key="holder"/></label>
                                            <i class="far fa-user"></i>
                                            <input id="card-holder" type="text" class="form-control"
                                                   placeholder="<fmt:message key="holder"/>" aria-label="Card Holder"
                                                   aria-describedby="basic-addon1" required>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-5">
                                        <label><fmt:message key="exp.date"/></label>
                                        <div class="input-group expiration-date">
                                            <input type="text" class="form-control" placeholder="MM" aria-label="MM"
                                                   aria-describedby="basic-addon1"
                                                   pattern="^(0[1-9]|1[012])$" required>
                                            <span class="date-separator">/</span>
                                            <input type="number" class="form-control" placeholder="<fmt:message key="year"/>" aria-label="YY"
                                                   aria-describedby="basic-addon1"
                                                   min="<ctg:year/>" max="<ctg:expYear/>" required>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-8">
                                        <div class="inner-addon right-addon">
                                            <label for="card-number"><fmt:message key="card.num"/></label>
                                            <i class="far fa-credit-card"></i>
                                            <input id="card-number" type="text" class="form-control"
                                                   placeholder="<fmt:message key="card.num"/>" aria-label="Card Holder"
                                                   aria-describedby="basic-addon1"
                                                   maxlength="16" pattern="[0-9]{16}" required>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-4">
                                        <label for="cvc">CVC</label>
                                        <input id="cvc" type="password" class="form-control" placeholder="CVC"
                                               aria-label="Card Holder" aria-describedby="basic-addon1" maxlength="3"
                                               pattern="^\d{3}$" required>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <input type="hidden" name="order_id" value="${param.order_id}">
                                        <input type="hidden" name="total_price" value="${param.amount}">
                                        <button type="submit" class="btn btn-primary btn-block"><fmt:message key="card.proceed"/></button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div><!--/col-lg-6 -->

                </div><!--/row -->
            </div><!--/card-box -->
        </div><!--/card-holder -->

    </div><!--/container -->
</section>
</body>