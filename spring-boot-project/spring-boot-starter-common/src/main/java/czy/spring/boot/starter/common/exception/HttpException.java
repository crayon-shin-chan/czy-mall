package czy.spring.boot.starter.common.exception;

/**
 * 业务异常类
 */
public class HttpException extends RuntimeException {

    private IError IError;

    public HttpException(IError IError) {
        super(IError.getMessage());
        this.IError = IError;
    }

    public IError getIError() {
        return IError;
    }

}
