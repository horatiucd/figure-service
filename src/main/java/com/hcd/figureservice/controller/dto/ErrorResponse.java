package com.hcd.figureservice.controller.dto;

public record ErrorResponse(String title, int status, String detail, String instance) {
}
