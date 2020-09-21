import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FirstAppTestModule } from '../../../test.module';
import { PasajeroComponent } from 'app/entities/pasajero/pasajero.component';
import { PasajeroService } from 'app/entities/pasajero/pasajero.service';
import { Pasajero } from 'app/shared/model/pasajero.model';

describe('Component Tests', () => {
  describe('Pasajero Management Component', () => {
    let comp: PasajeroComponent;
    let fixture: ComponentFixture<PasajeroComponent>;
    let service: PasajeroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [PasajeroComponent],
      })
        .overrideTemplate(PasajeroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PasajeroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PasajeroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pasajero(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pasajeros && comp.pasajeros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
