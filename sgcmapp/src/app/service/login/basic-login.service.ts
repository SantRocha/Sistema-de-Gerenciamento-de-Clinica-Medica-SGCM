import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Usuario } from '../../model/usuario';
import { ILoginService } from './i-login.service';

@Injectable({
  providedIn: 'root'
})
export class BasicLoginService implements ILoginService {

  constructor() {
    const userData = sessionStorage.getItem('usuario') || '{}';
    const usuario = JSON.parse(userData);
    this.usuarioAutenticado.next(usuario);
  }

  usuarioAutenticado: BehaviorSubject<Usuario> = new BehaviorSubject<Usuario>(<Usuario>{});
  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);

  login(usuario: Usuario): void {

    const credenciaisCodificadas = btoa(
      usuario.nomeUsuario + ':' + usuario.senha
    );
    const opcoesHttp = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + credenciaisCodificadas
      })
    };
    const url = environment.API_URL + '/login';

    this.http.get<Usuario>(url, opcoesHttp).subscribe({
      next: (usuario: Usuario) => {
        sessionStorage.setItem('usuario', JSON.stringify(usuario));
        this.usuarioAutenticado.next(usuario);
      },
      complete: () => {
        this.router.navigate(['/']);
      }
    });

  }

  logout(): void {
    sessionStorage.removeItem('usuario');
    document.cookie = 'XSRF-TOKEN=; Max-Age=0; path=/';
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    const userData = sessionStorage.getItem('usuario') || '{}';
    const usuario = JSON.parse(userData);
    return Object.keys(usuario).length > 0;
  }

  getHeaders(request: HttpRequest<any>): HttpRequest<any> {
    return request.clone({
      withCredentials: true,
      headers: request.headers.set('X-Requested-With', 'XMLHttpRequest')
    })
  }

}
