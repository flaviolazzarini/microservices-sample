package li.lazzarini.microservices_sample.micro;

public enum ErrorMessage {
    GENERIC_ERROR("An error has occured"),
    INTERNAL_ERROR("Internal Server error");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
