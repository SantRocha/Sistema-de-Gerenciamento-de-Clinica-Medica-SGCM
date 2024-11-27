import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { LoginService } from './login/i-login.service';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  if (!loginService.isLoggedIn()) {
    loginService.logout();
    return false;
  }
  const papelExigido = route.data['papel'];
  const papelUsuario = loginService.usuarioAutenticado.value.papel;
  const podeAcessar = papelExigido == papelUsuario || !papelExigido;
  return podeAcessar;
};
