import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { AvionDetailComponent } from 'app/entities/avion/avion-detail.component';
import { Avion } from 'app/shared/model/avion.model';

describe('Component Tests', () => {
  describe('Avion Management Detail Component', () => {
    let comp: AvionDetailComponent;
    let fixture: ComponentFixture<AvionDetailComponent>;
    const route = ({ data: of({ avion: new Avion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AvionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
