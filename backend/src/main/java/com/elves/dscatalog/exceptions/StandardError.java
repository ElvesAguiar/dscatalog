package com.elves.dscatalog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;


}
