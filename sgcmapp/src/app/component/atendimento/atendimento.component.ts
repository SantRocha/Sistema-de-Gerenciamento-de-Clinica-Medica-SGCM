import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { Atendimento } from '../../model/atendimento';
import { ETipoAlerta } from '../../model/e-tipo-alerta';
import { RequisicaoPaginada } from '../../model/requisicao-paginada';
import { RespostaPaginada } from '../../model/resposta-paginada';
import { AlertaService } from '../../service/alerta.service';
import { AtendimentoService } from '../../service/atendimento.service';
import { BarraComandosComponent } from '../barra-comandos/barra-comandos.component';
import { IList } from '../i-list';
import { TheadOrdenacao } from '../thead-ordenacao/thead-ordenacao';
import { TheadOrdenacaoComponent } from '../thead-ordenacao/thead-ordenacao.component';

@Component({
  selector: 'app-atendimento',
  standalone: true,
  imports: [BarraComandosComponent, CommonModule, NgbPaginationModule, TheadOrdenacaoComponent, FormsModule],
  templateUrl: './atendimento.component.html',
  styles: ``
})
export class AtendimentoComponent implements IList<Atendimento> {

  constructor(
    private servico: AtendimentoService,
    private servicoAlerta: AlertaService
  ) {}

  ngOnInit(): void {
    this.requisicaoPaginada.size = parseInt(localStorage.getItem('tamanhoPagina') || '5');
    this.get();
  }

  registros: Atendimento[] = [];
  respostaPaginada: RespostaPaginada<Atendimento> = <RespostaPaginada<Atendimento>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'data', descricao: 'Data' },
    { campo: 'hora', descricao: 'Hora' },
    { campo: 'paciente.nome', descricao: 'Paciente' },
    { campo: 'profissional.nome', descricao: 'Profissional' },
    { campo: 'unidade.nome', descricao: 'Unidade' },
    { campo: 'convenio.nome', descricao: 'Convênio' },
    { campo: '', descricao: 'Atendimento' },
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

  get(termoBusca?: string | undefined): void {
    this.servico.get(termoBusca, this.requisicaoPaginada).subscribe({
      next: (resposta: RespostaPaginada<Atendimento>) => {
        this.registros = resposta.content
          .filter(item => {
            return ['CHEGADA', 'ATENDIMENTO'].includes(item.status);
          })
          // .filter(item => {
          //   let data = new Date().setHours(0, 0, 0, 0);
          //   let hoje = new Date(data).toISOString().split('T')[0];
          //   return item.data == hoje;
          // });
        this.respostaPaginada = resposta;
      }
    });
  }
  
  delete(id: number): void {
    throw new Error('Method not implemented.');
  }

  updateStatus(id: number): void {
    if (confirm('Confirma alteração no status do atendimento?')) {
      this.servico.updateStatus(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "O status do atendimento foi alterado!"
          });
        }
      });
    }  
  }

}
