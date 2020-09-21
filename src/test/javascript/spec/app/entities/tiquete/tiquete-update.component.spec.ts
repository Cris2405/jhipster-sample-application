import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { TiqueteUpdateComponent } from 'app/entities/tiquete/tiquete-update.component';
import { TiqueteService } from 'app/entities/tiquete/tiquete.service';
import { Tiquete } from 'app/shared/model/tiquete.model';

describe('Component Tests', () => {
  describe('Tiquete Management Update Component', () => {
    let comp: TiqueteUpdateComponent;
    let fixture: ComponentFixture<TiqueteUpdateComponent>;
    let service: TiqueteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [TiqueteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TiqueteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiqueteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiqueteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tiquete(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tiquete();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
