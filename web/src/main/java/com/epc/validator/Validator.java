package com.epc.validator;

import com.epc.exceptions.ValidateException;
import org.apache.log4j.Logger;

/**
 * This class validates data from html or jsp
 * by checking length and type of fields
 */
public class Validator {
    public static Logger log = Logger.getLogger(Validator.class);

    public static boolean validateString(String string) {
        boolean check=true;
        if ( (string==null)||(string.length()>255)) {
            check=false;
        }
        return check;
    }

    public static boolean validateString(String string, int fieldSize) {
        boolean check=true;
        if ( (string==null)||(string.length()>fieldSize)) {
            check=false;
        }
        return check;
    }

    public static boolean validateNotEmptyString(String string) {
        boolean check=true;
        if (string.isEmpty()||(!validateString(string))) {
            check=false;
        }
        return check;
    }

    public static boolean validateNotEmptyString(String string, int fieldSize) {
        boolean check=true;
        if (string.isEmpty()||(!validateString(string, fieldSize))) {
            check=false;
        }
        return check;
    }


    public static int validateInt(String string) throws ValidateException{
        int number=-1;
        if (validateNotEmptyString(string, 11)) {
            try {
                number = Integer.parseInt(string);
                if (number<1) {
                    number = -1;
                }

            }catch (NumberFormatException e) {
                throw new ValidateException(e);
            }
        }
        return number;
    }

    public static long validateLong(String string) throws ValidateException{
        long number=-1;
        if (validateNotEmptyString(string, 11)) {
            try {
                number = Long.parseLong(string);
                if (number<1) {
                    number = -1;
                }

            }catch (NumberFormatException e) {
                throw new ValidateException(e);
            }
        }
        return number;
    }
}        