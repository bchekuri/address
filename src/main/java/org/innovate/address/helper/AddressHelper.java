package org.innovate.address.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.innovate.address.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressHelper {

    public final static Logger LOG = LoggerFactory.getLogger(AddressHelper.class);

    public static String[] tokenize(String fullAddress) {
        if(StringUtils.isEmpty(fullAddress)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        fullAddress = RegExUtils.replaceAll(fullAddress, Constants.ADDRESS_REPLACE, Constants.AMP_SYMBOL);
        Pattern pattern = Pattern.compile(Constants.ADDRESS_TOKEN);
        Matcher matcher = pattern.matcher(fullAddress);
        List<String> tokens = new ArrayList<String>();
        while(matcher.find()) {
            LOG.debug("Each Token: {}", matcher.group(0));
            tokens.add(matcher.group(0));
        }
        return tokens.stream().toArray(String[]::new);
    }



}
