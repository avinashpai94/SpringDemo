//package com.example.demo.utils.exception;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class AppException {
//
//    /**
//     * Returns new RuntimeException based on template and args
//     *
//     * @param messageTemplate
//     * @param args
//     * @return
//     */
//    public static RuntimeException throwException(String messageTemplate, String... args) {
//        return new RuntimeException(format(messageTemplate, args));
//    }
//
//    /**
//     * Returns new RuntimeException based on EntityType, ExceptionType and args
//     *
//     * @param entityType
//     * @param exceptionType
//     * @param args
//     * @return
//     */
//    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
//        String messageTemplate = getMessageTemplate(entityType, exceptionType);
//        return throwException(exceptionType, messageTemplate, args);
//    }
//
//    /**
//     * Returns new RuntimeException based on EntityType, ExceptionType and args
//     *
//     * @param entityType
//     * @param exceptionType
//     * @param args
//     * @return
//     */
//    public static RuntimeException throwExceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
//        String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id.toString());
//        return throwException(exceptionType, messageTemplate, args);
//    }
//
//    /**
//     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
//     *
//     * @param entityType
//     * @param exceptionType
//     * @param messageTemplate
//     * @param args
//     * @return
//     */
//    public static RuntimeException throwExceptionWithTemplate(EntityType entityType, ExceptionType exceptionType, String messageTemplate, String... args) {
//        return throwException(exceptionType, messageTemplate, args);
//    }
//
//    /**
//     * Returns new RuntimeException based on template and args
//     *
//     * @param messageTemplate
//     * @param args
//     * @return
//     */
//    private static RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
//        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
//            return new EntityNotFoundException(format(messageTemplate, args));
//        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
//            return new DuplicateEntityException(format(messageTemplate, args));
//        } else if (ExceptionType.VALIDATION_ERROR.equals(exceptionType)) {
//            return new ValidationErrorException(format(messageTemplate, args));
//        }
//        return new RuntimeException(format(messageTemplate, args));
//    }
//
//    private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
//        return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
//    }
//
//    private static String format(String template, String ... args) {
//        return String.format(template, (Object[]) args);
//    }
//
//    public static class EntityNotFoundException extends RuntimeException {
//        public EntityNotFoundException(String message) {
//            super(message);
//        }
//    }
//
//    public static class DuplicateEntityException extends RuntimeException {
//        public DuplicateEntityException(String message) {
//            super(message);
//        }
//    }
//
//    public static class ValidationErrorException extends RuntimeException {
//        public ValidationErrorException(String message) {
//            super(message);
//        }
//    }
//
//}
