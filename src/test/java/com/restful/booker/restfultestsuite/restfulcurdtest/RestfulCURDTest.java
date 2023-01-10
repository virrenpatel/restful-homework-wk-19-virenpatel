package com.restful.booker.restfultestsuite.restfulcurdtest;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.restfulinfo.RestfulSteps;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class RestfulCURDTest extends TestBase {
    static String firstname = "Human" + TestUtils.getRandomValue();
    static String lastname = "Being" + TestUtils.getRandomValue();
    static int totalprice = Integer.parseInt(1 + TestUtils.getRandomValue());
    static boolean depositpaid = true;
    static String additionalneeds = "pillow";

    static String token;
    static int id;

    @Steps
    RestfulSteps restfulSteps;

    @Title("This method will create a Token")
    @Test
    public void test001() {
        ValidatableResponse response = restfulSteps.getTokken().statusCode(200);
        token = response.extract().path("token");
    }

    @Title("This method will create and verify a booking")
    @Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");
        ValidatableResponse response = restfulSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");

        ValidatableResponse validate = restfulSteps.getAllBookingIDs();
        ArrayList<?> booking = validate.extract().path("bookingid");
        Assert.assertTrue(booking.contains(id));
    }

    @Title("This method will get booking with Id")
    @Test
    public void test003() {
        restfulSteps.getSingleBookingIDs(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test004() {
        additionalneeds = "lunch";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");
        restfulSteps.updateBookingWithID(id, token, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        ValidatableResponse response = restfulSteps.getSingleBookingIDs(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue("lunch"));
    }

    @Title("This method will delete a booking with ID")
    @Test
    public void test005() {
        restfulSteps.deleteABookingID(id, token).statusCode(201);
        restfulSteps.getSingleBookingIDs(id).statusCode(404);
    }

}