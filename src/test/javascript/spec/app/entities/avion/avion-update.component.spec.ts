import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { AvionUpdateComponent } from 'app/entities/avion/avion-update.component';
import { AvionService } from 'app/entities/avion/avion.service';
import { Avion } from 'app/shared/model/avion.model';

describe('Component Tests', () => {
  describe('Avion Management Update Component', () => {
    let comp: AvionUpdateComponent;
    let fixture: ComponentFixture<AvionUpdateComponent>;
    let service: AvionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AvionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Avion(123);
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
        const entity = new Avion();
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
