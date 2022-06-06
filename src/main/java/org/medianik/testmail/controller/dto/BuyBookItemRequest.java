package org.medianik.testmail.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyBookItemRequest{
    private Long bookItemId;
    private Long amount;
}
