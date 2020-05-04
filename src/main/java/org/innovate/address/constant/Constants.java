package org.innovate.address.constant;

/**
 * @author BChekuri
 */
public class Constants {


    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
    public static final String CORRELATION_ID_HEADER = "Client-Correlation-Id";
    public static final String CONTEXT_ID = "ContextId";

    public static final String ADDRESS_REPLACE = "(&#38;)|(&amp;)";
    public static final String AMP_SYMBOL = "&";
    public static final String ADDRESS_TOKEN = "\\(*\\b[^\\s,;#&()]+[.,;)\\n]*|[#&]";

}
