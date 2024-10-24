import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Feature("Test Mealplan")
public class mealPlanTest extends BaseTestSpooner{

    String apiKey = "c5314486df714165bb55012ba70dd07d";
    String username = "satrio13";
    String hash = "dd09a6152a4eb409e8bbae8f26b6d22d12db9fee";

    @Test(description = "Test Meal Planner Generate")
    public void testMealPlannerGenerate(){
        given().queryParam("apiKey", apiKey)
                .log().ifValidationFails()
                .when()
                .get("/mealplanner/generate")
                .then()
                .statusCode(200)
                .body("week.monday.meals.size()", equalTo(3))
                .extract().response();
    }

    @Test(description = "Test Meal Planner Generate With Parameter")
    public void testMealPlannerParameterGenerate(){
        given().queryParam("apiKey", apiKey)
                .queryParam("targetCalories", 1000)
                .queryParam("diet", "vegetarian")
                .log().ifValidationFails()
                .when()
                .get("/mealplanner/generate")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test(description = "Add Mealplan")
    public void testAddMealPlan(){
        String requestBody = "{\n" +
                "    \"date\": 1589500800,\n" +
                "    \"slot\": 1,\n" +
                "    \"position\": 0,\n" +
                "    \"type\": \"INGREDIENTS\",\n" +
                "    \"value\": {\n" +
                "        \"ingredients\": [\n" +
                "            {\n" +
                "                \"name\": \"1 banana\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
            given().contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .queryParam("apiKey", apiKey)
                    .queryParam("hash", hash)
                    .body(requestBody)
                    .when()
                    .post("/mealplanner/{username}/items", username)
                    .then()
                    .statusCode(200)
                    .extract().response();

    }

    @Test(description = "Test Image Classification")
    public void testImageClassification(){
        given().queryParam("apiKey", apiKey)
                .queryParam("imageUrl", "https://asset.kompas.com/crops/UhV2ngrlUWo92yJyruxM7I-vSNE=/69x65:869x598/750x500/data/photo/2021/11/25/619f7dc86e939.jpg")
                .log().ifValidationFails()
                .when()
                .get("/food/images/classify")
                .then()
                .statusCode(200)
                .extract().response();
    }


}
