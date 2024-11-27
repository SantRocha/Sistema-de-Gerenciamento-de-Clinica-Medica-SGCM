import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Profissional } from '../../../model/profissional';
import { RequisicaoPaginada } from '../../../model/requisicao-paginada';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { AlertaService } from '../../../service/alerta.service';
import { ProfissionalService } from '../../../service/profissional.service';
import { BarraComandosComponent } from '../../barra-comandos/barra-comandos.component';
import { IList } from '../../i-list';
import { TheadOrdenacaoComponent } from '../../thead-ordenacao/thead-ordenacao.component';
import { TheadOrdenacao } from '../../thead-ordenacao/thead-ordenacao';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profissional-list',
  standalone: true,
  imports: [
    CommonModule,
    BarraComandosComponent,
    RouterLink,
    NgbPaginationModule,
    TheadOrdenacaoComponent,
    FormsModule],
  templateUrl: './profissional-list.component.html',
  styles: ``
})
export class ProfissionalListComponent implements IList<Profissional> {

  constructor(
    private servico: ProfissionalService,
    private servicoAlerta: AlertaService
  ) { }

  ngOnInit(): void {
    this.get();
  }

  registros: Profissional[] = Array<Profissional>();
  respostaPaginada: RespostaPaginada<Profissional> = <RespostaPaginada<Profissional>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'id', descricao: 'ID' },
    { campo: 'nome', descricao: 'Nome' },
    { campo: 'registroConselho', descricao: 'Registro' },
    { campo: 'especialidade.nome', descricao: 'Especialidade' },
    { campo: 'unidade.nome', descricao: 'Unidade' },
    { campo: 'telefone', descricao: 'Telefone' },
    { campo: 'email', descricao: 'E-mail' },
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
      next: (resposta: RespostaPaginada<Profissional>) => {
        this.registros = resposta.content;
        this.respostaPaginada = resposta;
      }
    });
  }

  delete(id: number): void {
    if (confirm('Confirma a exclusão do profissional?')) {
      this.servico.delete(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Profissional excluído com sucesso!"
          });
        }
      });
    }
  }

}
