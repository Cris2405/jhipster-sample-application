import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FirstAppTestModule } from '../../../test.module';
import { AeropuertoUpdateComponent } from 'app/entities/aeropuerto/aeropuerto-update.component';
import { AeropuertoService } from 'app/entities/aeropuerto/aeropuerto.service';
import { Aeropuerto } from 'app/shared/model/aeropuerto.model';

describe('Component Tests', () => {
  describe('Aeropuerto Management Update Component', () => {
    let comp: AeropuertoUpdateComponent;
    let fixture: ComponentFixture<AeropuertoUpdateComponent>;
    let service: AeropuertoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FirstAppTestModule],
        declarations: [AeropuertoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AeropuertoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AeropuertoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AeropuertoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aeropuerto(123);
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
        const entity = new Aeropuerto();
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
