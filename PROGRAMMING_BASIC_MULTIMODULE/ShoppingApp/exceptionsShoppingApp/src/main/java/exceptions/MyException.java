package exceptions;

public class MyException extends RuntimeException {

    private ExceptionInfo exceptionInfo;
    public MyException(ExceptionCode exceptionCode, String message) {
        this.exceptionInfo = new ExceptionInfo(exceptionCode, message);
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }
}
