import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { PasajeroDetailComponent } from 'app/entities/pasajero/pasajero-detail.component';
import { Pasajero } from 'app/shared/model/pasajero.model';

describe('Component Tests', () => {
  describe('Pasajero Management Detail Component', () => {
    let comp: PasajeroDetailComponent;
    let fixture: ComponentFixture<PasajeroDetailComponent>;
    const route = ({ data: of({ pasajero: new Pasajero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [PasajeroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PasajeroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PasajeroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pasajero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pasajero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
