import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FirstAppTestModule } from '../../../test.module';
import { VueloComponent } from 'app/entities/vuelo/vuelo.component';
import { VueloService } from 'app/entities/vuelo/vuelo.service';
import { Vuelo } from 'app/shared/model/vuelo.model';

describe('Component Tests', () => {
  describe('Vuelo Management Component', () => {
    let comp: VueloComponent;
    let fixture: ComponentFixture<VueloComponent>;
    let service: VueloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [VueloComponent],
      })
        .overrideTemplate(VueloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VueloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VueloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vuelo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vuelos && comp.vuelos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
