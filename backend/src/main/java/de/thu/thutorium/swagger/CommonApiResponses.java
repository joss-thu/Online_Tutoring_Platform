package de.thu.thutorium.swagger;

import de.thu.thutorium.exceptions.SpringErrorPayload;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to centralize common API response annotations.
 *
 * <p>This annotation includes multiple {@link ApiResponse} annotations, each of which references
 * the {@link SpringErrorPayload} class to describe the structure of error responses. When you apply
 * the CommonApiResponses annotation to a controller method, it automatically includes these
 * common @ApiResponse annotations in the Swagger documentation for that method.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
    value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class))),
      @ApiResponse(
          responseCode = "201",
          description = "Resource created successfully",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class))),
      @ApiResponse(
          responseCode = "204",
          description = "Resource deleted successfully",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(
          responseCode = "400",
          description = "Bad Request",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class))),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class))),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class))),
      @ApiResponse(
          responseCode = "404",
          description = "Not Found",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class))),
      @ApiResponse(
          responseCode = "409",
          description = "Conflict",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class))),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SpringErrorPayload.class)))
    })
public @interface CommonApiResponses { }
