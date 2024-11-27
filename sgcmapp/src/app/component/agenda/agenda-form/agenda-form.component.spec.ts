import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { AtendimentoService } from '../../../service/atendimento.service';
import { AgendaFormComponent } from './agenda-form.component';

const mockAtendimentos = [
  {
    id: 1,
    data: '2024-08-12',
    hora: '14:00',
    profissional_id: 1,
    paciente_id: 2
  }
];

const serviceMock = {
  getById: jasmine.createSpy('getById').and.returnValue(of(mockAtendimentos[0])),
  save: jasmine.createSpy('save').and.returnValue(of(mockAtendimentos[0]))
};

const routerMock = {
  navigate: jasmine.createSpy('navigate').and.returnValue(Promise.resolve(true))
};

const mockActivatedRoute = {
  snapshot: {
    queryParamMap: {
      get: jasmine.createSpy('get')
    }
  }
};

describe('AgendaFormComponent', () => {
  let component: AgendaFormComponent;
  let fixture: ComponentFixture<AgendaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        AgendaFormComponent
      ],
      providers: [
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: AtendimentoService, useValue: serviceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    jasmine.clock().install();
    jasmine.clock().mockDate(new Date('2024-07-01'));

    fixture = TestBed.createComponent(AgendaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(function() {
      jasmine.clock().uninstall();
      mockActivatedRoute.snapshot.queryParamMap.get.calls.reset();
      serviceMock.getById.calls.reset();
  });

  it('deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  it('deve marcar o campo de data como inválido se a data não for um dia útil', () => {
    const dateInput = fixture.debugElement.query(By.css('input[name="data"]')).nativeElement;
    dateInput.value = '2024-08-10';
    dateInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    expect(component.formAgenda.get('data')?.hasError('diaUtil')).toBeTrue();
  });

  it('deve exibir a mensagem "Não é um dia útil" se a data não for um dia útil', () => {
    const dateInput = fixture.debugElement.query(By.css('input[name="data"]')).nativeElement;
    dateInput.value = '2024-08-10';
    dateInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    dateInput.dispatchEvent(new Event('blur'));
    fixture.detectChanges();
    const smallElements = fixture.debugElement.queryAll(By.css('input[name="data"] ~ small'));
    const errorMessage = smallElements.find(el => el.nativeElement.textContent.includes('Não é um dia útil'));
    expect(errorMessage).toBeTruthy();
  });

  it('deve ter o atributo "min" definido como a data atual no campo de data', () => {
    const dateInput = fixture.debugElement.query(By.css('input[name="data"]')).nativeElement;
    expect(dateInput.getAttribute('min')).toBe('2024-07-01');
  });
  

  it('deve exibir a mensagem "Campo obrigatório" para o campo de profissional se nenhum valor for selecionado', () => {
    const profissionalSelect = fixture.debugElement.query(By.css('select[name="profissional"]')).nativeElement;
    profissionalSelect.value = '';
    profissionalSelect.dispatchEvent(new Event('change'));
    profissionalSelect.dispatchEvent(new Event('blur'));
    fixture.detectChanges();
    const smallElements = fixture.debugElement.queryAll(By.css('select[name="profissional"] ~ small'));
    const errorMessage = smallElements.find(el => el.nativeElement.textContent.includes('Campo obrigatório'));
    expect(errorMessage).toBeTruthy();
  });

  it('deve exibir a mensagem "Campo obrigatório" para o campo de paciente se nenhum valor for selecionado', () => {
    const pacienteSelect = fixture.debugElement.query(By.css('select[name="paciente"]')).nativeElement;
    pacienteSelect.value = '';
    pacienteSelect.dispatchEvent(new Event('change'));
    pacienteSelect.dispatchEvent(new Event('blur'));
    fixture.detectChanges();
    const smallElements = fixture.debugElement.queryAll(By.css('select[name="paciente"] ~ small'));
    const errorMessage = smallElements.find(el => el.nativeElement.textContent.includes('Campo obrigatório'));
    expect(errorMessage).toBeTruthy();
  });

  it('deve exibir a mensagem "Campo obrigatório" para o campo de hora se nenhum valor for selecionado', () => {
    const horaSelect = fixture.debugElement.query(By.css('select[name="hora"]')).nativeElement;
    horaSelect.value = '';
    horaSelect.dispatchEvent(new Event('change'));
    horaSelect.dispatchEvent(new Event('blur'));
    fixture.detectChanges();
    const smallElements = fixture.debugElement.queryAll(By.css('select[name="hora"] ~ small'));
    const errorMessage = smallElements.find(el => el.nativeElement.textContent.includes('Campo obrigatório'));
    expect(errorMessage).toBeTruthy();
  });

  it('deve marcar o campo de profissional como inválido se nenhum valor for selecionado', () => {
    const profissionalControl = component.formAgenda.get('profissional_id');
    expect(profissionalControl)
      .withContext('O controle "profissional_id" deve existir no formulário.')
      .toBeTruthy();
    if (profissionalControl) {
      (profissionalControl as any).setValue(null);
      fixture.detectChanges();
      expect(profissionalControl.invalid).toBeTrue();
    }
  });

  it('deve marcar o campo de paciente como inválido se nenhum valor for selecionado', () => {
    const pacienteControl = component.formAgenda.get('paciente_id');
    expect(pacienteControl)
      .withContext('O controle "paciente_id" deve existir no formulário.')
      .toBeTruthy();
    if (pacienteControl) {
      (pacienteControl as any).setValue(null);
      fixture.detectChanges();
      expect(pacienteControl.invalid).toBeTrue();
    }
  });

  it('deve marcar o campo de data como inválido se nenhum valor for fornecido', () => {
    const dataControl = component.formAgenda.get('data');
    expect(dataControl)
      .withContext('O controle "data" deve existir no formulário.')
      .toBeTruthy();
    if (dataControl) {
      (dataControl as any).setValue('');
      fixture.detectChanges();
      expect(dataControl.invalid).toBeTrue();
    }
  });

  it('deve marcar o campo de hora como inválido se nenhum valor for selecionado', () => {
    const horaControl = component.formAgenda.get('hora');
    expect(horaControl)
      .withContext('O controle "hora" deve existir no formulário.')
      .toBeTruthy();
    if (horaControl) {
      (horaControl as any).setValue('');
      fixture.detectChanges();
      expect(horaControl.invalid).toBeTrue();
    }
  });

  it('deve marcar o formulário como inválido se algum campo obrigatório estiver vazio', () => {
    const profissionalControl = component.formAgenda.get('profissional_id') as any;
    const pacienteControl = component.formAgenda.get('paciente_id') as any;
    const dataControl = component.formAgenda.get('data') as any;
    const horaControl = component.formAgenda.get('hora') as any;
  
    expect(profissionalControl)
      .withContext('O controle "profissional_id" deve existir no formulário.')
      .toBeTruthy();
  
    expect(pacienteControl)
      .withContext('O controle "paciente_id" deve existir no formulário.')
      .toBeTruthy();
  
    expect(dataControl)
      .withContext('O controle "data" deve existir no formulário.')
      .toBeTruthy();
  
    expect(horaControl)
      .withContext('O controle "hora" deve existir no formulário.')
      .toBeTruthy();
  
    if (profissionalControl && pacienteControl && dataControl && horaControl) {
      profissionalControl.setValue(null);
      pacienteControl.setValue(null);
      dataControl.setValue('');
      horaControl.setValue('');
      fixture.detectChanges();
      expect(component.formAgenda.invalid).toBeTrue();
    }
  });
  

  it('deve marcar o formulário como válido se todos os campos obrigatórios estiverem preenchidos', () => {
    const profissionalControl = component.formAgenda.get('profissional_id') as any;
    const pacienteControl = component.formAgenda.get('paciente_id') as any;
    const dataControl = component.formAgenda.get('data') as any;
    const horaControl = component.formAgenda.get('hora') as any;
  
    expect(profissionalControl)
      .withContext('O controle "profissional_id" deve existir no formulário.')
      .toBeTruthy();
  
    expect(pacienteControl)
      .withContext('O controle "paciente_id" deve existir no formulário.')
      .toBeTruthy();
  
    expect(dataControl)
      .withContext('O controle "data" deve existir no formulário.')
      .toBeTruthy();
  
    expect(horaControl)
      .withContext('O controle "hora" deve existir no formulário.')
      .toBeTruthy();
  
    if (profissionalControl && pacienteControl && dataControl && horaControl) {
      profissionalControl.setValue(1);
      pacienteControl.setValue(1);
      dataControl.setValue('2024-08-13');
      horaControl.setValue('14:00');
      fixture.detectChanges();
      expect(component.formAgenda.valid).toBeTrue();
    }
  });

  it('deve carregar os dados no formulário ao inicializar o componente', () => {
    const profissionalControl = component.formAgenda.get('profissional_id') as any;
    const pacienteControl = component.formAgenda.get('paciente_id') as any;
    const dataControl = component.formAgenda.get('data') as any;
    const horaControl = component.formAgenda.get('hora') as any

    mockActivatedRoute.snapshot.queryParamMap.get.and.returnValue('1');
    component.ngOnInit();
  
    expect(profissionalControl?.value).toBe(1);
    expect(pacienteControl?.value).toBe(2);
    expect(dataControl?.value).toBe('2024-08-12');
    expect(horaControl?.value).toBe('14:00');
  });

  it('deve atualizar o registro com os valores do formulário ao submeter o formulário', () => {
    const profissionalControl = component.formAgenda.get('profissional_id') as any;
    const pacienteControl = component.formAgenda.get('paciente_id') as any;
    const dataControl = component.formAgenda.get('data') as any;
    const horaControl = component.formAgenda.get('hora') as any;

    profissionalControl.setValue(1);
    pacienteControl.setValue(1);
    dataControl.setValue('2024-08-13');
    horaControl.setValue('14:00');
    fixture.detectChanges();

    component.save();
    expect(component.registro.profissional_id).toBe(1);
    expect(component.registro.paciente_id).toBe(1);
    expect(component.registro.data).toBe('2024-08-13');
    expect(component.registro.hora).toBe('14:00');
  });
});
