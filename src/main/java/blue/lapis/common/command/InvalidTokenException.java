package blue.lapis.common.command;

/**
 *
 */
public class InvalidTokenException extends RuntimeException {
    private String   token;
    private Class<?> expectedType;
    public InvalidTokenException(String token, Class<?> expectedType) {
        this.token = token;
        this.expectedType = expectedType;
    }

    /**
     * @return the token that the TokenParser attempted to resolve
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the Class of the object which the TokenParser creates
     */
    public Class<?> getExpectedType() {
        return expectedType;
    }
}
