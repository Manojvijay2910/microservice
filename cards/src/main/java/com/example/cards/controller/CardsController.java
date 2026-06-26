package com.example.cards.controller;

import com.example.cards.constants.CardsConstants;
import com.example.cards.dto.CardsContactInfoDto;
import com.example.cards.dto.CardsDto;
import com.example.cards.dto.ErrorResponseDto;
import com.example.cards.dto.ResponseDto;
import com.example.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Cards",
    description = "CRUD REST APIs for card details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private final ICardsService iCardsService;

    public CardsController(ICardsService iCardsService) {
        this.iCardsService = iCardsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new card"
    )
    @ApiResponse(
        responseCode = CardsConstants.STATUS_201,
        description = CardsConstants.MESSAGE_201
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                          @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                          String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch Card details based on a mobile number"
    )
    @ApiResponse(
        responseCode = CardsConstants.STATUS_200,
        description = CardsConstants.MESSAGE_200
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                           @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                           String mobileNumber) {

        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cardsDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer & Account details based on a account number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = CardsConstants.STATUS_200,
            description = CardsConstants.MESSAGE_200
        ),
        @ApiResponse(
            responseCode = CardsConstants.STATUS_417,
            description = CardsConstants.MESSAGE_417_UPDATE
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {

        boolean isUpdated = iCardsService.updateCard(cardsDto);

        if(isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer & Account details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = CardsConstants.STATUS_200,
            description = CardsConstants.MESSAGE_200
        ),
        @ApiResponse(
            responseCode = CardsConstants.STATUS_417,
            description = CardsConstants.MESSAGE_417_DELETE
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                        String mobileNumber) {

        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into loans microservice"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = CardsConstants.STATUS_200,
            description = CardsConstants.MESSAGE_200
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java version details that is installed into loans microservice"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = CardsConstants.STATUS_200,
            description = CardsConstants.MESSAGE_200
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issue"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = CardsConstants.STATUS_200,
            description = CardsConstants.MESSAGE_200
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
