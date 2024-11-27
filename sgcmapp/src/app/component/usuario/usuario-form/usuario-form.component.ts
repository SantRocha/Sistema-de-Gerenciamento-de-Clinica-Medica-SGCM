import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Usuario } from '../../../model/usuario';
import { AlertaService } from '../../../service/alerta.service';
import { UsuarioService } from '../../../service/usuario.service';
import { IForm } from '../../i-form';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './usuario-form.component.html',
  styles: ``
})
export class UsuarioFormComponent implements IForm<Usuario> {

  constructor(
      private servico: UsuarioService,
      private servicoAlerta: AlertaService,
      private router: Router,
      private route: ActivatedRoute) { }

  ngOnInit(): void {

    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Usuario) => {
          this.registro = resposta;
          this.formUsuario.patchValue(this.registro);
        }
      });
    }

  }

  registro: Usuario = <Usuario>{};

  formUsuario = new FormGroup({
    nomeCompleto: new FormControl<string | null>(null),
    nomeUsuario: new FormControl<string | null>(null),
    senha: new FormControl<string | null>(null),
    ativo: new FormControl<boolean | null>(null),
    papel: new FormControl<string | null>(null)
  });  
  
  get form() {
    return this.formUsuario.controls;
  }
  
  save(): void {
    this.registro = Object.assign(this.registro, this.formUsuario.value);
    this.servico.save(this.registro).subscribe({
      complete: () => {
        this.router.navigate(['/config/usuarios']);
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Operação realizada com sucesso!"
        });
      }
    });
  }

}
