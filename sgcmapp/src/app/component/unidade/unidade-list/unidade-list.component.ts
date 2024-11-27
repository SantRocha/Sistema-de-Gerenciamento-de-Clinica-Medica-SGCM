import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { RequisicaoPaginada } from '../../../model/requisicao-paginada';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { Unidade } from '../../../model/unidade';
import { AlertaService } from '../../../service/alerta.service';
import { UnidadeService } from '../../../service/unidade.service';
import { BarraComandosComponent } from '../../barra-comandos/barra-comandos.component';
import { IList } from '../../i-list';
import { TheadOrdenacao } from '../../thead-ordenacao/thead-ordenacao';
import { TheadOrdenacaoComponent } from '../../thead-ordenacao/thead-ordenacao.component';

@Component({
  selector: 'app-unidade-list',
  standalone: true,
  imports: [CommonModule, BarraComandosComponent, RouterLink, NgbPaginationModule, TheadOrdenacaoComponent, FormsModule],
  templateUrl: './unidade-list.component.html',
  styles: ``
})
export class UnidadeListComponent implements IList<Unidade> {

  constructor(
    private servico: UnidadeService,
    private servicoAlerta: AlertaService
  ) { }

  ngOnInit(): void {
    this.requisicaoPaginada.size = parseInt(localStorage.getItem('tamanhoPagina') || '5');
    this.get();
  }

  registros: Unidade[] = Array<Unidade>();
  respostaPaginada: RespostaPaginada<Unidade> = <RespostaPaginada<Unidade>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'id', descricao: 'ID' },
    { campo: 'nome', descricao: 'Nome' },
    { campo: 'endereco', descricao: 'Endereço' },
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
      next: (resposta: RespostaPaginada<Unidade>) => {
        this.registros = resposta.content;
        this.respostaPaginada = resposta;
      }
    });
  }

  delete(id: number): void {
    if (confirm('Confirma a exclusão da unidade?')) {
      this.servico.delete(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Unidade excluída com sucesso!"
          });
        }
      });
    }
  }

}
