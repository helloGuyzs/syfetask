package com.helloguyzs.syfetask.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionReponse {

    private List<CreateTransactionResponse> transactions;
}
