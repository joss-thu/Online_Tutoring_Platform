package de.thu.thutorium.exceptions;

public class UserNameNotFoundException extends RuntimeException {
  //  private static final String MESSAGE = ": Username not found";
  /**
   * Constructs a new UserNameNotFoundException with a default message. It calls the superclass
   * constructor super(MESSAGE), passing the default message "Username not found" to the
   * RuntimeException class.
   */
  public UserNameNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
