import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Convenio } from '../../../model/convenio';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { AlertaService } from '../../../service/alerta.service';
import { ConvenioService } from '../../../service/convenio.service';
import { IForm } from '../../i-form';

@Component({
  selector: 'app-convenio-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './convenio-form.component.html',
  styles: ``
})
export class ConvenioFormComponent implements IForm<Convenio> {

  constructor(
      private servico: ConvenioService,
      private servicoAlerta: AlertaService,
      private router: Router,
      private route: ActivatedRoute) { }

  ngOnInit(): void {

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Convenio) => {
          this.registro = resposta;
          this.formConvenio.patchValue(this.registro);
        }
      });
    }

    this.registro.ativo = this.registro.ativo || false;

  }

  registro: Convenio = <Convenio>{};

  formConvenio = new FormGroup({
    nome: new FormControl<string | null>(null),
    razaoSocial: new FormControl<string | null>(null),
    cnpj: new FormControl<string | null>(null),
    representante: new FormControl<string | null>(null),
    email: new FormControl<string | null>(null, Validators.email),
    telefone: new FormControl<string | null>(null),
    ativo: new FormControl<boolean | null>(null)
  });  
  
  get form() {
    return this.formConvenio.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formConvenio.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/convenios']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
