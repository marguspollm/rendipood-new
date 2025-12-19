package ee.margus.rendipood.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerAdviceHandlerTest {

    @Test
    void HandleException() {
        ControllerAdviceHandler handler = new ControllerAdviceHandler();
        RuntimeException ex = new RuntimeException("Something went wrong");

        ResponseEntity<ErrorMessage> response = handler.handleException(ex);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).isEqualTo("Something went wrong");
        assertThat(response.getBody().getDate()).isInstanceOf(Date.class);
    }
}