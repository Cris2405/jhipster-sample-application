import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { AeropuertoDetailComponent } from 'app/entities/aeropuerto/aeropuerto-detail.component';
import { Aeropuerto } from 'app/shared/model/aeropuerto.model';

describe('Component Tests', () => {
  describe('Aeropuerto Management Detail Component', () => {
    let comp: AeropuertoDetailComponent;
    let fixture: ComponentFixture<AeropuertoDetailComponent>;
    const route = ({ data: of({ aeropuerto: new Aeropuerto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AeropuertoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AeropuertoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AeropuertoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aeropuerto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aeropuerto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
