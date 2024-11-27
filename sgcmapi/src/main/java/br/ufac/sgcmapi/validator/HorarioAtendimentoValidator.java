package br.ufac.sgcmapi.validator;

import java.time.LocalTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HorarioAtendimentoValidator implements ConstraintValidator<HorarioAtendimento, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        LocalTime hora = LocalTime.parse(value);
        LocalTime limiteInicial = LocalTime.of(14, 0);
        LocalTime limiteFinal = LocalTime.of(20, 0);

        boolean naoEstaAntesDoLimiteInicial = !hora.isBefore(limiteInicial);
        boolean naoEstaDepoisDoLimiteFinal = !hora.isAfter(limiteFinal);
        
        return naoEstaAntesDoLimiteInicial && naoEstaDepoisDoLimiteFinal;
        
    }
    
}
