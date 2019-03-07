package com.cxyz.check.nexception;

import com.cxyz.check.enums.BusinessExceptionEnum;
import com.cxyz.check.enums.ResultCode;


/**
 * @desc 业务异常类
 * 
 * @author zhumaer
 * @since 9/18/2017 3:00 PM
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 194906846739586856L;

	protected String code;

	protected String message;

	protected ResultCode resultCode;

	protected Object data;

	public BusinessException() {
		BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.getByEClass(this.getClass());
		if (exceptionEnum != null) {
			resultCode = exceptionEnum.getResultCode();
			code = exceptionEnum.getResultCode().code().toString();
			message = exceptionEnum.getResultCode().message();
		}

	}

	public BusinessException(String message) {
		this();
		this.message = message;
	}

	public BusinessException(String format, Object... objects) {
		this();
		this.message = formatIfArgs(format, "{}", objects);
	}

	public BusinessException(ResultCode resultCode, Object data) {
		this(resultCode);
		this.data = data;
	}

	public BusinessException(ResultCode resultCode) {
		this.resultCode = resultCode;
		this.code = resultCode.code().toString();
		this.message = resultCode.message();
	}

	/**
	 *  格式化字符串（替换符为%s）
	 */
	private String formatIfArgs(String format, Object... args) {
		if (format == null || format.isEmpty()) {
			return format;
		}

		return (args == null || args.length == 0)  ? String.format(format.replaceAll("%([^n])", "%%$1")) : String.format(format, args);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
