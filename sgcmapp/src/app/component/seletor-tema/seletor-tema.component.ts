import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-seletor-tema',
  standalone: true,
  imports: [FormsModule, NgbDropdownModule, CommonModule],
  templateUrl: './seletor-tema.component.html',
  styles: `
    button[ngbDropdownToggle] {
      width: 11em;
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-right: .25em;
    }
  `
})
export class SeletorTemaComponent {

  temaSelecionado: string = '';
  temas: { [key: string]: string } = {
    azul: 'Azul',
    vermelho: 'Vermelho',
    amarelo: 'Amarelo'
  }

  listaTemas(): string[] {
    return Object.keys(this.temas);
  }

  selecionarTema(tema: string): void {
    this.temaSelecionado = tema;
    this.mudarTema();
  }

  ngOnInit(): void {
    this.temaSelecionado = localStorage.getItem('tema') || '';
    if (this.temaSelecionado) {
      this.mudarTema();
    }
  }

  mudarTema(): void {
    if (this.temaSelecionado) {
      let url = "/assets/css/estilo-tema-" + this.temaSelecionado + ".css";
      let linkTema = document.querySelector<HTMLLinkElement>("#link-tema");
      if (linkTema) {
        linkTema.href = url;
      }
      localStorage.setItem('tema', this.temaSelecionado);
    }
  }

}
