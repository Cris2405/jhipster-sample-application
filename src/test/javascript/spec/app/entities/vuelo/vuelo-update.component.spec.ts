import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { VueloUpdateComponent } from 'app/entities/vuelo/vuelo-update.component';
import { VueloService } from 'app/entities/vuelo/vuelo.service';
import { Vuelo } from 'app/shared/model/vuelo.model';

describe('Component Tests', () => {
  describe('Vuelo Management Update Component', () => {
    let comp: VueloUpdateComponent;
    let fixture: ComponentFixture<VueloUpdateComponent>;
    let service: VueloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [VueloUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VueloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VueloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VueloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vuelo(123);
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
        const entity = new Vuelo();
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
