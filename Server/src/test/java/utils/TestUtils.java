package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestUtils {

    @Test
    void generateAccessToken() {
        String token1 = Utils.generateAccessToken();
        String token2 = Utils.generateAccessToken();

        // ASSERT
        assertNotEquals(token1, token2);
    }
}