package dev.mediaforum.network.conf;

import org.hibernate.service.spi.ServiceException;

public class RestException extends ServiceException {

    public RestException(String message) {
        super(message);
    }
}
