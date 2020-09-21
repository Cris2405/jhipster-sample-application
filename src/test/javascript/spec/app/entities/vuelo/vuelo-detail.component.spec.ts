import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { VueloDetailComponent } from 'app/entities/vuelo/vuelo-detail.component';
import { Vuelo } from 'app/shared/model/vuelo.model';

describe('Component Tests', () => {
  describe('Vuelo Management Detail Component', () => {
    let comp: VueloDetailComponent;
    let fixture: ComponentFixture<VueloDetailComponent>;
    const route = ({ data: of({ vuelo: new Vuelo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [VueloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VueloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VueloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vuelo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vuelo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
