import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Especialidade } from '../../../model/especialidade';
import { Profissional } from '../../../model/profissional';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { Unidade } from '../../../model/unidade';
import { AlertaService } from '../../../service/alerta.service';
import { EspecialidadeService } from '../../../service/especialidade.service';
import { ProfissionalService } from '../../../service/profissional.service';
import { UnidadeService } from '../../../service/unidade.service';
import { IForm } from '../../i-form';
import { notBlankValidator } from '../../../validators/not-blank.validator';

@Component({
  selector: 'app-profissional-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './profissional-form.component.html',
  styles: ``
})
export class ProfissionalFormComponent implements IForm<Profissional> {

  constructor(
    private servico: ProfissionalService,
    private servicoAlerta: AlertaService,
    private servicoEspecialidade: EspecialidadeService,
    private servicoUnidade: UnidadeService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.servicoEspecialidade.get().subscribe({
      next: (resposta: RespostaPaginada<Especialidade>) => {
        this.especialidades = resposta.content;
      }
    });

    this.servicoUnidade.get().subscribe({
      next: (resposta: RespostaPaginada<Unidade>) => {
        this.unidades = resposta.content;
      }
    });

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Profissional) => {
          this.registro = resposta;
          this.formProfissional.patchValue(this.registro);
        }
      });
    }

  }

  registro: Profissional = <Profissional>{};
  especialidades: Especialidade[] = [];
  unidades: Unidade[] = [];

  formProfissional = new FormGroup({
    nome: new FormControl<string | null>(null, notBlankValidator(3)),
    registroConselho: new FormControl<string | null>(null),
    especialidade_id: new FormControl<number | null>(null),
    unidade_id: new FormControl<number | null>(null),
    telefone: new FormControl<string | null>(null, Validators.pattern("^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")),
    email: new FormControl<string | null>(null, Validators.email)
  });  
  
  get form() {
    return this.formProfissional.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formProfissional.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/profissionais']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
