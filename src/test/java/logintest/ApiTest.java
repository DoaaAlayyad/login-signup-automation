package logintest;
/* 
  Execute API: ✔️
RestAssured.get(...)

JSON: ✔️
response.getBody().asString()
jsonPath().getList(...)

Response: ✔️
Response response

AssertTrue: ✔️
assertTrue()
مصطلحات مهمة 
API Response	رد السيرفر بعد ما نبعث طلب
JSON Array	قائمة من العناصر داخل JSON
Assertion	التحقق من النتيجة المتوقعة
Parsing	تحويل الرد من JSON إلى كائن قابل للتعامل
 */
import io.restassured.RestAssured;//لإرسال طلبات HTTP واستلام الردود
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    private final String BASE_URL = "https://reqres.in/api/users";

    @Order(1)
    @DisplayName("✅ التحقق من كود الاستجابة 200")

    @Test

    public void testStatusCode() {
        Response response = RestAssured.get(BASE_URL + "?page=2");
        int statusCode = response.statusCode();
        System.out.println("Actual status code = " + statusCode);
        assertTrue(statusCode == 200, "Status code is not 200!");
    }

    @Order(2)
    @DisplayName("✅ يتحقق من وجود المستخدم Michael")
    @Test
    public void testUserExists() {
        Response response = RestAssured.get(BASE_URL + "?page=2");
        String body = response.getBody().asString();
        assertTrue(body.contains("Michael"), "User 'Michael' not found in response!");
    }
    
    @Order(3)
    @DisplayName("✅ يتحقق من أن عدد المستخدمين في الصفحة هو 6")
    @Test
    public void testUserCount() {
        Response response = RestAssured.get(BASE_URL + "?page=2");
        int size = response.jsonPath().getList("data").size();
        assertTrue(size == 6, "Expected 6 users but found " + size);
    }
}
