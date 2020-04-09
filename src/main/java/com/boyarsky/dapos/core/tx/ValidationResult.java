package com.boyarsky.dapos.core.tx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
    private String message;
    private int code;
    private Transaction tx;
    private Exception e;
}
