import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Especialidade } from '../../../model/especialidade';
import { AlertaService } from '../../../service/alerta.service';
import { EspecialidadeService } from '../../../service/especialidade.service';
import { IForm } from '../../i-form';

@Component({
  selector: 'app-especialidade-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './especialidade-form.component.html',
  styles: ``
})
export class EspecialidadeFormComponent implements IForm<Especialidade> {

  constructor(
      private servico: EspecialidadeService,
      private servicoAlerta: AlertaService,
      private router: Router,
      private route: ActivatedRoute) { }

  ngOnInit(): void {

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Especialidade) => {
          this.registro = resposta;
          this.formEspecialidade.patchValue(this.registro);
        }
      });
    }

  }

  registro: Especialidade = <Especialidade>{};

  formEspecialidade = new FormGroup({
    nome: new FormControl<string | null>(null)
  });  
  
  get form() {
    return this.formEspecialidade.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formEspecialidade.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/config/especialidades']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
