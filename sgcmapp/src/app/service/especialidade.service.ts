import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Especialidade } from '../model/especialidade';
import { RequisicaoPaginada } from '../model/requisicao-paginada';
import { RespostaPaginada } from '../model/resposta-paginada';
import { IService } from './i-service';

@Injectable({
  providedIn: 'root'
})
export class EspecialidadeService implements IService<Especialidade> {

  constructor(
    private http: HttpClient
  ) { }

  apiUrl: string = environment.API_URL + '/config/especialidade/';

  get(termoBusca?: string | undefined, paginacao?: RequisicaoPaginada | undefined): Observable<RespostaPaginada<Especialidade>> {
    let url = this.apiUrl + "?";
    if (termoBusca) {
      url += "termoBusca=" + termoBusca;
    }
    if (paginacao) {
      url += "&page=" + paginacao.page;
      url += "&size=" + paginacao.size;
      paginacao.sort.forEach(campo => {
        url += "&sort=" + campo;
      });
    } else {
      url += "&unpaged=true";
    }
    return this.http.get<RespostaPaginada<Especialidade>>(url);
  }

  getById(id: number): Observable<Especialidade> {
    let url = this.apiUrl + id;
    return this.http.get<Especialidade>(url);
  }

  save(objeto: Especialidade): Observable<Especialidade> {
    let url = this.apiUrl;
    if (objeto.id) {
      return this.http.put<Especialidade>(url, objeto);
    } else {
      return this.http.post<Especialidade>(url, objeto);
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }

}
