import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Paciente } from '../../../model/paciente';
import { AlertaService } from '../../../service/alerta.service';
import { PacienteService } from '../../../service/paciente.service';
import { IForm } from '../../i-form';

@Component({
  selector: 'app-paciente-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './paciente-form.component.html',
  styles: ``
})
export class PacienteFormComponent implements IForm<Paciente> {

  constructor(
      private servico: PacienteService,
      private servicoAlerta: AlertaService,
      private router: Router,
      private route: ActivatedRoute) { }

  ngOnInit(): void {

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Paciente) => {
          this.registro = resposta;
          this.formPaciente.patchValue(this.registro);
        }
      });
    }

  }

  registro: Paciente = <Paciente>{};

  formPaciente = new FormGroup({
    nome: new FormControl<string | null>(null),
    email: new FormControl<string | null>(null, Validators.email),
    telefone: new FormControl<string | null>(null),
    dataNascimento: new FormControl<string | null>(null),
    grupoSanguineo: new FormControl<string | null>(null),
    sexo: new FormControl<string | null>(null),
    cep: new FormControl<string | null>(null),
    endereco: new FormControl<string | null>(null),
    estado: new FormControl<string | null>(null),
    cidade: new FormControl<string | null>(null)
  });  
  
  get form() {
    return this.formPaciente.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formPaciente.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/pacientes']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
