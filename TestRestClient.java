import org.springframework.web.client.RestClient;

public class TestRestClient {
    public static void main(String[] args) {
        RestClient restClient = RestClient.create();
        try {
            System.out.println("Calling...");
            Object response = restClient.patch()
                    .uri("http://localhost:8082/internal/courses/1/reserve-seat")
                    .retrieve()
                    .body(Object.class);
            System.out.println("Success: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
