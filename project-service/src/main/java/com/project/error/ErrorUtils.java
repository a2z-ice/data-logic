package com.project.error;

public class ErrorUtils {
    public static EntityNotFoundException throwEntityNotFoundException(Long id){
        return new EntityNotFoundException("given " + id + " project id not found");
    }
}
