import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { RequisicaoPaginada } from '../../../model/requisicao-paginada';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { Usuario } from '../../../model/usuario';
import { AlertaService } from '../../../service/alerta.service';
import { UsuarioService } from '../../../service/usuario.service';
import { BarraComandosComponent } from '../../barra-comandos/barra-comandos.component';
import { IList } from '../../i-list';
import { TheadOrdenacao } from '../../thead-ordenacao/thead-ordenacao';
import { TheadOrdenacaoComponent } from '../../thead-ordenacao/thead-ordenacao.component';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, BarraComandosComponent, RouterLink, NgbPaginationModule, TheadOrdenacaoComponent, FormsModule],
  templateUrl: './usuario-list.component.html',
  styles: ``
})
export class UsuarioListComponent implements IList<Usuario> {

  constructor(
    private servico: UsuarioService,
    private servicoAlerta: AlertaService
  ) { }

  ngOnInit(): void {
    this.requisicaoPaginada.size = parseInt(localStorage.getItem('tamanhoPagina') || '5');
    this.get();
  }

  registros: Usuario[] = Array<Usuario>();
  respostaPaginada: RespostaPaginada<Usuario> = <RespostaPaginada<Usuario>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'id', descricao: 'ID' },
    { campo: 'nomeCompleto', descricao: 'Nome' },
    { campo: 'nomeUsuario', descricao: 'Usuário' },
    { campo: 'ativo', descricao: 'Ativo' },
    { campo: 'papel', descricao: 'Papel' },
    { campo: '', descricao: 'Ações' },
  ];

  mudarPagina(pagina: number): void {
    this.requisicaoPaginada.page = pagina - 1;
    this.get(this.termoBusca);
  }

  ordenar(ordenacao: string[]): void {
    this.requisicaoPaginada.sort = ordenacao;
    this.requisicaoPaginada.page = 0;
    this.get(this.termoBusca);
  }

  mudarTamanhoPagina() {
    localStorage.setItem('tamanhoPagina', this.requisicaoPaginada.size.toString());
    this.get(this.termoBusca);
  }

 get(termoBusca?: string): void {
    this.termoBusca = termoBusca;
    this.servico.get(termoBusca, this.requisicaoPaginada).subscribe({
      next: (resposta: RespostaPaginada<Usuario>) => {
        this.registros = resposta.content;
        this.respostaPaginada = resposta;
      }
    });
  }

  delete(id: number): void {
    if (confirm('Confirma a exclusão do usuário?')) {
      this.servico.delete(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Usuário excluído com sucesso!"
          });
        }
      });
    }
  }

}
