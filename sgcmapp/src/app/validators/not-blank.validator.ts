import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function notBlankValidator(minLength: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        minLength = minLength || 1;
        if (control.value == null || control.value?.trim().length < minLength) {
            return { notBlank: true }
        }
        return null;
    }
}
