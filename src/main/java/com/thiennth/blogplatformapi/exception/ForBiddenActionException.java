package com.thiennth.blogplatformapi.exception;

public class ForBiddenActionException extends RuntimeException {
    
    public ForBiddenActionException() {
        super("This user is forbidden to perform this action");
    }
}
