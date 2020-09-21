import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { TiqueteDetailComponent } from 'app/entities/tiquete/tiquete-detail.component';
import { Tiquete } from 'app/shared/model/tiquete.model';

describe('Component Tests', () => {
  describe('Tiquete Management Detail Component', () => {
    let comp: TiqueteDetailComponent;
    let fixture: ComponentFixture<TiqueteDetailComponent>;
    const route = ({ data: of({ tiquete: new Tiquete(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [TiqueteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TiqueteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TiqueteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tiquete on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tiquete).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
