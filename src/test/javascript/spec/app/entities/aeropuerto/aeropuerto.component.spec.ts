import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FirstAppTestModule } from '../../../test.module';
import { AeropuertoComponent } from 'app/entities/aeropuerto/aeropuerto.component';
import { AeropuertoService } from 'app/entities/aeropuerto/aeropuerto.service';
import { Aeropuerto } from 'app/shared/model/aeropuerto.model';

describe('Component Tests', () => {
  describe('Aeropuerto Management Component', () => {
    let comp: AeropuertoComponent;
    let fixture: ComponentFixture<AeropuertoComponent>;
    let service: AeropuertoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AeropuertoComponent],
      })
        .overrideTemplate(AeropuertoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AeropuertoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AeropuertoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Aeropuerto(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aeropuertos && comp.aeropuertos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
