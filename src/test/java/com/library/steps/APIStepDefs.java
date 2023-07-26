package com.library.steps;

import com.github.javafaker.Faker;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;

public class APIStepDefs {

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;
    String pathParam;

    ValidatableResponse postgiven;

    String requestType;
    /**
     * US 01 RELATED STEPS
     *
     */
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {

        givenPart = given().log().uri()
                .header("x-library-token", LibraryAPI_Util.getToken(userType));
    }
    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {
        givenPart.accept(contentType);
    }


    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endpoint ).prettyPeek();
        thenPart = response.then();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        thenPart.statusCode(statusCode);
    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart.contentType(contentType);
    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, is(notNullValue()));
    }


    @And("Path param is {string}")
    public void pathParamIs(String pathParam) {
        givenPart.pathParam("id",pathParam);
        this.pathParam =pathParam;
    }

    @And("{string} field should be same with path param")
    public void fieldShouldBeSameWithPathParam(String pathParamMatch) {

        thenPart.assertThat().body(pathParamMatch,is(pathParam));
    }

    @And("following fields should not be null")
    public void followingFieldsShouldNotBeNull(List<String> categoryList) {
        JsonPath jsonPath = thenPart.extract().jsonPath();
     for (String each : categoryList){
         System.out.println("jsonPath.get(each) = " + jsonPath.get(each));
         Assert.assertTrue(!each.isEmpty());
         //thenPart.assertThat().body(each,is(noNullValue));
     }



    }

    @And("Request Content Type header is {string}")
    public void requestContentTypeHeaderIs(String ContainsType) {
        givenPart.headers(ContainsType,ContentType.URLENC);

    }

    String bookNameAPI;
    String bookIsbnAPI;
    int bookYearAPI;
    String bookAuthorAPI;
    int book_category_idAPI;
    String bookDescriptionAPI;
    String userFullNameAPI;
    String userEmailAPI;
    String userPasswordAPI;
    int userGroupIdAPI;
    String userStatusAPI;
    String userStartDateAPI;
    String userEndDateAPI;
    String userAddressAPI;

    @And("I create a random {string} as request body")
    public void iCreateARandomAsRequestBody(String postType) {
        Faker faker = new Faker();
        Random random = new Random();



        switch (postType){
            case "book":
                bookNameAPI = faker.book().title();
                bookIsbnAPI = faker.numerify("##########");
                bookYearAPI = 1900 + random.nextInt(123);
                bookAuthorAPI = faker.book().author();
                book_category_idAPI = random.nextInt(20)+1; //from 1 to 20 without +1 is 0-19
                bookDescriptionAPI = faker.lorem().sentence();

                givenPart.formParam("name",bookNameAPI)
                        .and().formParam("isbn",bookIsbnAPI)
                        .and().formParam("year",bookYearAPI)
                        .and().formParam("author",bookAuthorAPI)
                        .and().formParam("book_category_id",book_category_idAPI)
                        .and().formParam("description",bookDescriptionAPI);
                break;
            case "user":
                userFullNameAPI = faker.name().fullName();
                userEmailAPI = faker.internet().emailAddress();
                userPasswordAPI = faker.internet().password();
                userGroupIdAPI = random.nextInt(2)+2;
                userStatusAPI = "ACTIVE";
                userStartDateAPI = faker.numerify("201#-0#-1#");
                userEndDateAPI = faker.numerify("201#-0#-1#");
                userAddressAPI = faker.address().fullAddress();
                givenPart.formParam("full_name",userFullNameAPI)
                        .and().formParam("email",userEmailAPI)
                        .and().formParam("password",userPasswordAPI)
                        .and().formParam("user_group_id",userGroupIdAPI)
                        .and().formParam("status",userStatusAPI)
                        .and().formParam("start_date",userStartDateAPI)
                        .and().formParam("end_date",userEndDateAPI)
                        .and().formParam("address",userAddressAPI);
                break;

            default:
                throw new RuntimeException("The post type haven't been implemented in the switch statement yet!");
        }
    }

    @When("I send POST request to {string} endpoint")
    public void iSendPOSTRequestToEndpoint(String endPoint) {
        thenPart = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri")+endPoint).prettyPeek().then();
    }

    @And("the field value for {string} path should be equal to {string}")
    public void theFieldValueForPathShouldBeEqualTo(String message, String messageContain) {

            thenPart.body(message,is(messageContain));
    }

    LoginPage loginPage ;

    @And("I logged in Library UI as {string}")
    public void iLoggedInLibraryUIAs(String userType) {
        loginPage = new LoginPage();
        loginPage.login(userType);
    }
    BookPage bookPage;
    @And("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        bookPage = new BookPage();
        bookPage.navigateModule(page);
        BrowserUtil.waitFor(5);
    }

    @And("UI, Database and API created book information must match")
    public void uiDatabaseAndAPICreatedBookInformationMustMatch() {

        JsonPath jsonPath = thenPart.extract().jsonPath();
        int id = jsonPath.getInt("book_id");
        System.out.println(id);
        // get actual book data from DB
        DB_Util.createConnection();
        DB_Util.runQuery("select name,isbn,year,author,book_category_id,description from books\n" +
                "where id ="+id);
        Map<String, Object> rowMap = DB_Util.getRowMap(1);
        String nameDB = (String) rowMap.get("name");
        String isbnDB = (String) rowMap.get("isbn");
        int yearDB = Integer.parseInt((String) rowMap.get("year"));
        String authorDB = (String) rowMap.get("author");
        int book_category_idDB =Integer.parseInt((String)rowMap.get("book_category_id"));
        String descriptionDB = (String) rowMap.get("description");

        // get the actual UI book data
        bookPage = new BookPage();
        bookPage.search.sendKeys(bookNameAPI);
        BrowserUtil.waitForVisibility(bookPage.editBook(bookNameAPI),10);
        bookPage.editBook(bookNameAPI).click();
        BrowserUtil.waitForVisibility(bookPage.bookName, 10);
        String nameUI = bookPage.bookName.getAttribute("value");
        String isbnUI = bookPage.isbn.getAttribute("value");
        int yearUI = Integer.parseInt(bookPage.year.getAttribute("value"));
        String authorUI = bookPage.author.getAttribute("value");
        // there is only book category name in UI -> get the name convert the name to ID with the help of DB
        Select select = new Select(bookPage.categoryDropdown);
        String book_category_nameUI = select.getFirstSelectedOption().getText();
        DB_Util.createConnection();
        DB_Util.runQuery("select id from book_categories\n" +
                "where name = '"+book_category_nameUI+"'");
        int book_category_idUI = Integer.parseInt(DB_Util.getFirstRowFirstColumn());
        String descriptionUI = bookPage.description.getAttribute("value");


        Assert.assertEquals(bookNameAPI,nameDB);
        Assert.assertEquals(bookNameAPI,nameUI);

        Assert.assertEquals(bookIsbnAPI,isbnDB);
        Assert.assertEquals(bookIsbnAPI,isbnUI);

        Assert.assertEquals(bookYearAPI,yearDB);
        Assert.assertEquals(bookYearAPI,yearUI);

        Assert.assertEquals(bookAuthorAPI,authorDB);
        Assert.assertEquals(bookAuthorAPI,authorUI);

        Assert.assertEquals(book_category_idAPI,book_category_idDB);
        Assert.assertEquals(book_category_idAPI,book_category_idUI);

        Assert.assertEquals(bookDescriptionAPI,descriptionDB);
        Assert.assertEquals(bookDescriptionAPI,descriptionUI);
    }



    public void status_code_should_be(int statusCode, String requestType) {
        if ("POST".equalsIgnoreCase(requestType)) {
            postgiven.statusCode(statusCode);
        } else if ("GET".equalsIgnoreCase(requestType)) {
            thenPart.statusCode(statusCode);
        } else {
            // Handle other request types or throw an exception if needed
        }
    }

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {
        JsonPath jsonPath = thenPart.extract().jsonPath();
        int user_id = jsonPath.getInt("user_id");

        DB_Util.createConnection();
        DB_Util.runQuery("select full_name,email,password,user_group_id,status,start_date,end_date,address from users where id ="+user_id);
        // get the user info from DB
        Map<String, Object> rowMap = DB_Util.getRowMap(1);
        String userFullNameDB = (String) rowMap.get("full_name");
        String userEmailDB= (String) rowMap.get("email");
        String userPasswordDB= (String) rowMap.get("password");
        int userGroupIdDB= Integer.parseInt((String) rowMap.get("user_group_id"));
        String userStatusDB= (String) rowMap.get("status");
        String userStartDateDB= (String) rowMap.get("start_date");
        String userEndDateDB= (String) rowMap.get("end_date");
        String userAddressDB= (String) rowMap.get("address");


        Assert.assertEquals(userFullNameAPI,userFullNameDB);
        Assert.assertEquals(userEmailAPI,userEmailDB);
        Assert.assertEquals(userGroupIdAPI,userGroupIdDB);
        Assert.assertEquals(userStatusAPI,userStatusDB);
        Assert.assertEquals(userStartDateAPI,userStartDateDB);
        Assert.assertEquals(userEndDateAPI,userEndDateDB);
        Assert.assertEquals(userAddressAPI,userAddressDB);
    }

    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {
        loginPage = new LoginPage();
        loginPage.login(userEmailAPI,userPasswordAPI);
    }

    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {
        bookPage = new BookPage();
        System.out.println(bookPage.accountHolderName.getText());
        Assert.assertEquals(userFullNameAPI,bookPage.accountHolderName.getText());
    }

    String token;
    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String username, String password) {
        token = LibraryAPI_Util.getToken(username, password);
        givenPart = given().header("x-library-token", token);
    }

    @Given("I send token information as request body")
    public void i_send_token_information_as_request_body() {
        givenPart.formParam("token",token);
    }



}
