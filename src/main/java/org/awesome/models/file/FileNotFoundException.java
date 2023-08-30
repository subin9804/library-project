package org.awesome.models.file;

import org.awesome.commons.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {


    public FileNotFoundException() {
        super(bundleError.getString("Not Found. File"), HttpStatus.BAD_REQUEST);
    }
}
