package com.wodeer.timesheet.util;

import com.wodeer.timesheet.exception.InvalidParameterException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * @author richard
 * @date 2019-02-28 13:54
 */
public final class RequestValidationUtil {
    public static void checkForm(BindingResult result) {
        if (result.hasErrors()) {
            String errMsg = result.getAllErrors()
                                  .stream()
                                  .map(ObjectError::getDefaultMessage)
                                  .collect(Collectors.joining("；"));

            throw new InvalidParameterException(errMsg);
        }
    }

//    public static void checkPage(Integer currentPage, Integer pageSize) {
//        if ()
//    }
}
