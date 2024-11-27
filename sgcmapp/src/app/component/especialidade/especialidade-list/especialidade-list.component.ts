import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Especialidade } from '../../../model/especialidade';
import { RequisicaoPaginada } from '../../../model/requisicao-paginada';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { AlertaService } from '../../../service/alerta.service';
import { EspecialidadeService } from '../../../service/especialidade.service';
import { BarraComandosComponent } from '../../barra-comandos/barra-comandos.component';
import { IList } from '../../i-list';
import { TheadOrdenacao } from '../../thead-ordenacao/thead-ordenacao';
import { TheadOrdenacaoComponent } from '../../thead-ordenacao/thead-ordenacao.component';

@Component({
  selector: 'app-especialidade-list',
  standalone: true,
  imports: [CommonModule, BarraComandosComponent, RouterLink, NgbPaginationModule, TheadOrdenacaoComponent, FormsModule],
  templateUrl: './especialidade-list.component.html',
  styles: ``
})
export class EspecialidadeListComponent implements IList<Especialidade> {

  constructor(
    private servico: EspecialidadeService,
    private servicoAlerta: AlertaService
  ) { }

  ngOnInit(): void {
    this.requisicaoPaginada.size = parseInt(localStorage.getItem('tamanhoPagina') || '5');
    this.get();
  }

  registros: Especialidade[] = Array<Especialidade>();
  respostaPaginada: RespostaPaginada<Especialidade> = <RespostaPaginada<Especialidade>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'id', descricao: 'ID' },
    { campo: 'nome', descricao: 'Nome' },
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
      next: (resposta: RespostaPaginada<Especialidade>) => {
        this.registros = resposta.content;
        this.respostaPaginada = resposta;
      }
    });
  }

  delete(id: number): void {
    if (confirm('Confirma a exclusão da especialidade?')) {
      this.servico.delete(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Especialidade excluída com sucesso!"
          });
        }
      });
    }
  }

}
