import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
public class RestBookersTests {
    @Test
    public void createBookingTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Crear un objeto Booking con espacios en firstname y lastname
        Booking booking = new Booking();
        booking.setFirstname("Eitan");
        booking.setLastname("de la Jaille");
        booking.setTotalprice(100);
        booking.setDepositpaid(true);
        booking.setBookingdates(new BookingDates("2024-09-01", "2024-09-10"));
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println("Payload: " + payload);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post("/booking");


        System.out.println("Response Body: " + response.getBody().asString());

        response.then().assertThat().statusCode(200);// Verificar que la respuesta es 200
        response.then().assertThat().body("firstname", Matchers.equalTo(booking.getFirstname()));
        response.then().assertThat().body("lastname", Matchers.equalTo(booking.getLastname()));
        response.then().assertThat().body("totalprice", Matchers.equalTo(booking.getTotalprice()));
        response.then().assertThat().body("depositpaid", Matchers.equalTo(booking.isDepositpaid()));
        response.then().assertThat().body("bookingdates", Matchers.equalTo(booking.getBookingdates()));
        response.then().assertThat().body("additionalneeds", Matchers.equalTo(booking.getAdditionalneeds()));
    }
    @Test
    public void createBookingWithNumericFirstAndLastNameTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Crear el payload JSON manualmente con números en firstname y lastname
        String payload = "{\n" +
                "    \"firstname\" : 12345,\n" +
                "    \"lastname\" : 67890,\n" +
                "    \"totalprice\" : 100,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-09-15\",\n" +
                "        \"checkout\" : \"2024-09-20\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";

        System.out.println("Payload: " + payload);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post("/booking");

        System.out.println("Response Body: " + response.getBody().asString());

        // Verificar si la API maneja correctamente los nombres con valores numéricos
        response.then().assertThat().statusCode(200); // Se espera un código 200 si se permite
        response.then().assertThat().body("firstname", Matchers.equalTo(12345));
        response.then().assertThat().body("lastname", Matchers.equalTo(67890));
        response.then().assertThat().body("totalprice", Matchers.equalTo(100));
        response.then().assertThat().body("depositpaid", Matchers.equalTo(true));
        response.then().assertThat().body("additionalneeds", Matchers.equalTo("Lunch"));
    }
    @Test
    public void getBookingWithInvalidIdTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Definir un ID fuera de rango, por ejemplo, 3000
        int invalidId = 3000;

        Response response = RestAssured
                .given().contentType(ContentType.JSON)
                .when().get("/booking/" + invalidId);

        System.out.println("Response Body: " + response.getBody().asString());

        // Verificar que la respuesta es 404 Not Found
        response.then().assertThat().statusCode(404);

    }
    @Test
    public void getBookingByIdTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Realizar un GET para obtener la reserva con ID 10
        Response response = RestAssured
                .given().get("/booking/10");

        System.out.println("Response Body: " + response.getBody().asString());

        // Verificar que la respuesta es 200
        response.then().assertThat().statusCode(200);

    }
    @Test
    public void updateBookingTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Definir el ID de la reserva existente que se va a actualizar
        int bookingId = 1;  // Asegúrate de que el ID existe en la base de datos

        // Crear un objeto Booking con los nuevos datos
        Booking updatedBooking = new Booking();
        updatedBooking.setFirstname("UpdatedName");
        updatedBooking.setLastname("UpdatedLastname");
        updatedBooking.setTotalprice(150);
        updatedBooking.setDepositpaid(false);
        updatedBooking.setBookingdates(new BookingDates("2024-09-15", "2024-09-20"));
        updatedBooking.setAdditionalneeds("Lunch");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(updatedBooking);
        System.out.println("Payload: " + payload);

        // Realizar la solicitud PUT para actualizar la reserva existente
        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().put("/booking/" + bookingId);

        System.out.println("Response Body: " + response.getBody().asString());

        // Verificar que la respuesta es 200 OK
        response.then().assertThat().statusCode(200);

        // Verificar que los datos fueron actualizados correctamente
        response.then().assertThat().body("firstname", equalTo(updatedBooking.getFirstname()));
        response.then().assertThat().body("lastname", equalTo(updatedBooking.getLastname()));
        response.then().assertThat().body("totalprice", equalTo(updatedBooking.getTotalprice()));
        response.then().assertThat().body("depositpaid", equalTo(updatedBooking.isDepositpaid()));
        response.then().assertThat().body("bookingdates.checkin", equalTo(updatedBooking.getBookingdates().getCheckin()));
        response.then().assertThat().body("bookingdates.checkout", equalTo(updatedBooking.getBookingdates().getCheckout()));
        response.then().assertThat().body("additionalneeds", equalTo(updatedBooking.getAdditionalneeds()));
    }
    @Test
    public void deleteBookingTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Definir el ID de la reserva existente que se va a eliminar
        int bookingId = 1;  // Asegúrate de que el ID existe en la base de datos

        // Realizar la solicitud DELETE para eliminar la reserva existente
        Response response = RestAssured
                .given().contentType(ContentType.JSON)
                .when().delete("/booking/" + bookingId);

        System.out.println("Response Body: " + response.getBody().asString());


        response.then().assertThat().statusCode(200);

        //Verificar que la reserva ya no existe haciendo un GET por ID
        Response getResponse = RestAssured
                .given().contentType(ContentType.JSON)
                .when().get("/booking/" + bookingId);

        getResponse.then().assertThat().statusCode(404);
    }
}
