package io.github.kenzor1979.mscreditassessor.application.ex;

import lombok.Getter;

public class MicroserviceCommunicationErrorException extends Exception {

    @Getter
    private Integer status;
    public MicroserviceCommunicationErrorException(String msg, Integer status) {
        super(msg);
        this.status = status;

    }
}
