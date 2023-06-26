package org.awesome.controllers.admin.books;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.awesome.constants.RentalStatus;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BookForm {

    private String mode = "write";
    @NotBlank
    private String bookId;

    private String RentalType;

    @NotBlank
    private String gid = "" + System.currentTimeMillis();


    @NotBlank
    private String bookNm;

    @NotBlank
    private String author;

    @NotBlank
    private String publisher;

    private String description;
}
