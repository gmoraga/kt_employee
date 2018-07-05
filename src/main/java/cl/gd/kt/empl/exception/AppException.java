package cl.gd.kt.empl.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
    private static final int CODE_INTERNAL_ERROR = 500;
    private final int code;

    public AppException(final String msg) {
        super(msg);
        this.code = CODE_INTERNAL_ERROR;

    }

    public int getCode() {
        return code;
    }
}
