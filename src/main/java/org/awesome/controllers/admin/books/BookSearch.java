package org.awesome.controllers.admin.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookSearch {
    private int page = 1;
    private int limit = 20;

    private String bookId;

    private String sopt; // 선택 옵션
    private String skey; // 키워드

    private String[] status;

    private String[] rentalType;
}
