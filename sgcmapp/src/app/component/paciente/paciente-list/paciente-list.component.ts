import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ETipoAlerta } from '../../../model/e-tipo-alerta';
import { Paciente } from '../../../model/paciente';
import { RequisicaoPaginada } from '../../../model/requisicao-paginada';
import { RespostaPaginada } from '../../../model/resposta-paginada';
import { AlertaService } from '../../../service/alerta.service';
import { PacienteService } from '../../../service/paciente.service';
import { BarraComandosComponent } from '../../barra-comandos/barra-comandos.component';
import { IList } from '../../i-list';
import { TheadOrdenacao } from '../../thead-ordenacao/thead-ordenacao';
import { TheadOrdenacaoComponent } from '../../thead-ordenacao/thead-ordenacao.component';

@Component({
  selector: 'app-paciente-list',
  standalone: true,
  imports: [CommonModule, BarraComandosComponent, RouterLink, NgbPaginationModule, TheadOrdenacaoComponent, FormsModule],
  templateUrl: './paciente-list.component.html',
  styles: ``
})
export class PacienteListComponent implements IList<Paciente> {

  constructor(
    private servico: PacienteService,
    private servicoAlerta: AlertaService
  ) { }

  ngOnInit(): void {
    this.requisicaoPaginada.size = parseInt(localStorage.getItem('tamanhoPagina') || '5');
    this.get();
  }

  registros: Paciente[] = Array<Paciente>();
  respostaPaginada: RespostaPaginada<Paciente> = <RespostaPaginada<Paciente>>{};
  requisicaoPaginada: RequisicaoPaginada = new RequisicaoPaginada();
  termoBusca: string | undefined = '';

  colunas: TheadOrdenacao = [
    { campo: 'id', descricao: 'ID' },
    { campo: 'nome', descricao: 'Nome' },
    { campo: 'email', descricao: 'E-mail' },
    { campo: 'telefone', descricao: 'Telefone' },
    { campo: 'dataNascimento', descricao: 'Data de nascimento' },
    { campo: 'grupoSanguineo', descricao: 'Grupo sanguíneo' },
    { campo: 'sexo', descricao: 'Sexo' },
    { campo: 'cep', descricao: 'cep' },
    { campo: 'endereco', descricao: 'Endereço' },
    { campo: 'estado', descricao: 'Estado' },
    { campo: 'cidade', descricao: 'Cidade' },
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
      next: (resposta: RespostaPaginada<Paciente>) => {
        this.registros = resposta.content;
        this.respostaPaginada = resposta;
      }
    });
  }

  delete(id: number): void {
    if (confirm('Confirma a exclusão do paciente?')) {
      this.servico.delete(id).subscribe({
        complete: () => {
          this.get();
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Paciente excluído com sucesso!"
          });
        }
      });
    }
  }

}
