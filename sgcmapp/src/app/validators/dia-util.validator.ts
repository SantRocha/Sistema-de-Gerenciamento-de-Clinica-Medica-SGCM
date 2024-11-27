import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function diaUtilValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const data = new Date(control.value);
        if (data.getDay() == 5 || data.getDay() == 6) {
            return { diaUtil: true };
        }
        return null;
    }
}
