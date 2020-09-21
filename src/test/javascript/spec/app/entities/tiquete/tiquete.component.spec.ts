import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FirstAppTestModule } from '../../../test.module';
import { TiqueteComponent } from 'app/entities/tiquete/tiquete.component';
import { TiqueteService } from 'app/entities/tiquete/tiquete.service';
import { Tiquete } from 'app/shared/model/tiquete.model';

describe('Component Tests', () => {
  describe('Tiquete Management Component', () => {
    let comp: TiqueteComponent;
    let fixture: ComponentFixture<TiqueteComponent>;
    let service: TiqueteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [TiqueteComponent],
      })
        .overrideTemplate(TiqueteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiqueteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiqueteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tiquete(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tiquetes && comp.tiquetes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
