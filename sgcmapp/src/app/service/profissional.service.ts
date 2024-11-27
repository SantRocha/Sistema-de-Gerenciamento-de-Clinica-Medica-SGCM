import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Profissional } from '../model/profissional';
import { RespostaPaginada } from '../model/resposta-paginada';
import { IService } from './i-service';
import { RequisicaoPaginada } from '../model/requisicao-paginada';

@Injectable({
  providedIn: 'root'
})
export class ProfissionalService implements IService<Profissional> {

  constructor(
    private http: HttpClient
  ) { }

  apiUrl: string = environment.API_URL + '/profissional/';

  get(termoBusca?: string | undefined, paginacao?: RequisicaoPaginada | undefined): Observable<RespostaPaginada<Profissional>> {
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
    return this.http.get<RespostaPaginada<Profissional>>(url);
  }

  getById(id: number): Observable<Profissional> {
    let url = this.apiUrl + id;
    return this.http.get<Profissional>(url);
  }

  save(objeto: Profissional): Observable<Profissional> {
    let url = this.apiUrl;
    if (objeto.id) {
      return this.http.put<Profissional>(url, objeto);
    } else {
      return this.http.post<Profissional>(url, objeto);
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }

}
