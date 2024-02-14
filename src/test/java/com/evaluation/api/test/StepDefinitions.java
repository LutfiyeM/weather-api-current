package com.evaluation.api.test;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.Integer.parseInt;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private ValidatableResponse validatableResponse;
    private RequestSpecification requestSpecification;

    @BeforeAll
    public static void setup() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://weatherapi-com.p.rapidapi.com";
        RestAssured.basePath = "/current.json";
    }


    @Given("I have a Realtime Weather API client")
    public void createApiClient() {
        requestSpecification = given()
                .header("X-RapidAPI-Key", "1be16e514bmsh64da2909410c9bdp1678c6jsne63ac0a7d932")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.JSON)
                .when();
    }

    @Given("I have a Realtime Weather API client with wrong credentials")
    public void createApiClientWithWrongCredentials() {
        requestSpecification = given()
                .header("X-RapidAPI-Key", "wrong api-key")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.JSON)
                .when();
    }

    /**
     * Send the GET request with query param
     *
     * @param key query param for location
     */
    @When("I search the {string} with a GET request")
    public void getRequest(String key) {
        validatableResponse = requestSpecification.queryParam("q", key).get().then();
    }

    /**
     * Check the status code of the response
     *
     * @param statusCode expected status code for the response
     */
    @Then("the API responded with status code {string}")
    public void checkStatusCode(String statusCode) {
        assertEquals(parseInt(statusCode), validatableResponse.extract().statusCode());
    }

    /**
     * Check if the city exists in the response
     *
     * @param city expected status code for the response
     */

    @And("the API response body has expected city {string} and country {string}")
    public void checkExpectedCity(String city, String country) {
        validatableResponse.body("location.name", equalTo(city));
        validatableResponse.body("location.country", equalTo(country));
    }

    @And("the API response body has expected error code {int} and message {string}")
    public void checkExpectedError(Integer errorCode, String errorMessage) {
        validatableResponse.body("error.code", equalTo(errorCode));
        validatableResponse.body("error.message", equalTo(errorMessage));
    }

    @And("the API response body has expected schema")
    public void checkExpectedSchema() {
        validatableResponse.body(matchesJsonSchemaInClasspath("fixtures/expected-schema.json"));
    }

}
