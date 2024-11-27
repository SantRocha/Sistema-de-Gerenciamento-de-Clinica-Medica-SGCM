import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Unidade } from '../../../model/unidade';
import { AlertaService } from '../../../service/alerta.service';
import { UnidadeService } from '../../../service/unidade.service';
import { IForm } from '../../i-form';

@Component({
  selector: 'app-unidade-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './unidade-form.component.html',
  styles: ``
})
export class UnidadeFormComponent implements IForm<Unidade> {

  constructor(
      private servico: UnidadeService,
      private servicoAlerta: AlertaService,
      private router: Router,
      private route: ActivatedRoute) { }

  ngOnInit(): void {

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Unidade) => {
          this.registro = resposta;
          this.formUnidade.patchValue(this.registro);
        }
      });
    }

  }

  registro: Unidade = <Unidade>{};

  formUnidade = new FormGroup({
    nome: new FormControl<string | null>(null),
    endereco: new FormControl<string | null>(null)
  });  
  
  get form() {
    return this.formUnidade.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formUnidade.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/config/unidades']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
