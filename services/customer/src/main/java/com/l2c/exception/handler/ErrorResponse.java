package com.l2c.exception.handler;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors

) {

}
