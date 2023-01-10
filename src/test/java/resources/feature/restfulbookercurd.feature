Feature: Restful Booker
  As a user I want to test Restful Booker HTTP Operations

  Scenario Outline: Check End to End CURD operations
    When I send POST request to create a new booking with firstName"<firstName>", lastName"<lastName>", totalprice "<totalprice>", depositpaid "<depositpaid>", checkin "<checkin>", checkout"<checkout>" additionalneeds "<additionalneeds>"
    Then I verfiy that new booking is created by id
    And I send a Put request with  firstName"<firstName>", lastName"<lastName>", totalprice "<totalprice>", depositpaid "<depositpaid>", checkin "<checkin>", checkout"<checkout>" additionalneeds "<additionalneeds>"
    And The totalprice "<totalprice>" is successfully updated
    And I send delete booking by id
    Then The booking is successfully deleted from the record
    Examples:
      | firstName | lastName | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Manan     | Shah     | 800        | yes         | 2024-09-01 | 2024-12-02 | veg food        |
      | Robert    | Taylor   | 400        | yes         | 2025-04-07 | 2025-04-08 | veg food        |



