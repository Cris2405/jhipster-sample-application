import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { PasajeroUpdateComponent } from 'app/entities/pasajero/pasajero-update.component';
import { PasajeroService } from 'app/entities/pasajero/pasajero.service';
import { Pasajero } from 'app/shared/model/pasajero.model';

describe('Component Tests', () => {
  describe('Pasajero Management Update Component', () => {
    let comp: PasajeroUpdateComponent;
    let fixture: ComponentFixture<PasajeroUpdateComponent>;
    let service: PasajeroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [PasajeroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PasajeroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PasajeroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PasajeroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pasajero(123);
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
        const entity = new Pasajero();
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
