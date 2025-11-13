package br.pucpr.mercadinho.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String codeDescription;
    private String message;


}
