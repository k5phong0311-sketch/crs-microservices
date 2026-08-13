package com.example.registrationservice.client;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class CourseClient {

    private final RestClient restClient;

    private final String COURSE_SERVICE =
            "http://localhost:8082";

    public void reserveSeat(Long courseId) {

        try {

            restClient.patch()
                    .uri(
                            COURSE_SERVICE
                                    + "/internal/courses/{id}/reserve-seat",
                            courseId
                    )
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {

                                if (response.getStatusCode().value() == 404) {
                                    throw new IllegalStateException(
                                            "Mon hoc khong ton tai"
                                    );
                                }

                                if (response.getStatusCode().value() == 409) {
                                    throw new IllegalStateException(
                                            "Mon hoc da het cho"
                                    );
                                }

                                throw new IllegalStateException(
                                        "Khong the dang ky mon hoc"
                                );
                            }
                    )
                    .toBodilessEntity();

        } catch (RestClientResponseException e) {

            if (e.getStatusCode().value() == 404) {
                throw new IllegalStateException(
                        "Mon hoc khong ton tai"
                );
            }

            if (e.getStatusCode().value() == 409) {
                throw new IllegalStateException(
                        "Mon hoc da het cho"
                );
            }

            throw new IllegalStateException(
                    "Khong the dang ky mon hoc"
            );

        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalStateException(
                    "Khong the ket noi toi course-service"
            );
        }
    }

    public void releaseSeat(Long courseId) {

        try {

            restClient.patch()
                    .uri(
                            COURSE_SERVICE
                                    + "/internal/courses/{id}/release-seat",
                            courseId
                    )
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException e) {

            throw new IllegalStateException(
                    "Khong the ket noi toi course-service"
            );
        }
    }
}