import java.time.LocalTime;

public class MyException extends RuntimeException {

    private String message;
    private ExceptionCode exceptionCode;
    private LocalTime exceptionTime;

    public MyException(ExceptionCode exceptionCode,String message ) {
        this.message = message;
        this.exceptionCode = exceptionCode;
        this.exceptionTime = LocalTime.now();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public LocalTime getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(LocalTime exceptionTime) {
        this.exceptionTime = exceptionTime;
    }
}
