package de.thu.thutorium.exceptions;

public class AuthenticationException extends RuntimeException {
  //  private static final String MESSAGE = ": Username not found";
  /**
   * Constructs a new AuthenticationException with a default message. It calls the superclass
   * constructor super(MESSAGE), passing the message to the RuntimeException class.
   */
  public AuthenticationException(String errorMessage) {
    super(errorMessage);
  }
}
