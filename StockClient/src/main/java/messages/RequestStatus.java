package messages;

public class RequestStatus {

    private String message;

    private int code;

    public RequestStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public RequestStatus() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
