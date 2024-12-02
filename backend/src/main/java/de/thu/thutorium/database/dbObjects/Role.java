package de.thu.thutorium.database.dbObjects;

/**
 * Enumeration representing the different roles a user can have.
 */
public enum Role {

    STUDENT,
    TUTOR,
    ADMIN,
    /**
     * Role for users who can verify a tutor.
     */
    VERIFIER

}
