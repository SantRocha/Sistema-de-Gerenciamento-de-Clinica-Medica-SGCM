import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { AlertaComponent } from './component/alerta/alerta.component';
import { SeletorTemaComponent } from './component/seletor-tema/seletor-tema.component';
import { Usuario } from './model/usuario';
import { ILoginService, LoginService } from './service/login/i-login.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    SeletorTemaComponent,
    AlertaComponent,
    CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  title = 'SGCM';
  currentUrl = '';
  usuario: Usuario = <Usuario>{};

  constructor(
      router: Router,
      @Inject(LoginService) private loginService: ILoginService) {

    router.events.subscribe(evento => {
      if (evento instanceof NavigationEnd) {
        this.currentUrl = evento.url;
      }
    });

    this.loginService.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario;
      }
    });

  }

  isLoggedIn(): boolean {
    return this.loginService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.usuario.papel == 'ROLE_ADMIN';
  }

  logout(): void {
    this.loginService.logout();
  }

}
