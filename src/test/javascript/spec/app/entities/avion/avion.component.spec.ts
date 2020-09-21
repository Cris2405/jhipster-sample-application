import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FirstAppTestModule } from '../../../test.module';
import { AvionComponent } from 'app/entities/avion/avion.component';
import { AvionService } from 'app/entities/avion/avion.service';
import { Avion } from 'app/shared/model/avion.model';

describe('Component Tests', () => {
  describe('Avion Management Component', () => {
    let comp: AvionComponent;
    let fixture: ComponentFixture<AvionComponent>;
    let service: AvionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AvionComponent],
      })
        .overrideTemplate(AvionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Avion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avions && comp.avions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
