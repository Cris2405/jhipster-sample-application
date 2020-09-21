import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPasajero, Pasajero } from 'app/shared/model/pasajero.model';
import { PasajeroService } from './pasajero.service';
import { IVuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from 'app/entities/vuelo/vuelo.service';
import { ITiquete } from 'app/shared/model/tiquete.model';
import { TiqueteService } from 'app/entities/tiquete/tiquete.service';

type SelectableEntity = IVuelo | ITiquete;

@Component({
  selector: 'jhi-pasajero-update',
  templateUrl: './pasajero-update.component.html',
})
export class PasajeroUpdateComponent implements OnInit {
  isSaving = false;
  vuelos: IVuelo[] = [];
  tiquetes: ITiquete[] = [];

  editForm = this.fb.group({
    id: [],
    idpas: [],
    nombre: [],
    identificacion: [],
    idt: [],
    idpas: [],
    tiquete: [],
  });

  constructor(
    protected pasajeroService: PasajeroService,
    protected vueloService: VueloService,
    protected tiqueteService: TiqueteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pasajero }) => {
      this.updateForm(pasajero);

      this.vueloService.query().subscribe((res: HttpResponse<IVuelo[]>) => (this.vuelos = res.body || []));

      this.tiqueteService.query().subscribe((res: HttpResponse<ITiquete[]>) => (this.tiquetes = res.body || []));
    });
  }

  updateForm(pasajero: IPasajero): void {
    this.editForm.patchValue({
      id: pasajero.id,
      idpas: pasajero.idpas,
      nombre: pasajero.nombre,
      identificacion: pasajero.identificacion,
      idt: pasajero.idt,
      idpas: pasajero.idpas,
      tiquete: pasajero.tiquete,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pasajero = this.createFromForm();
    if (pasajero.id !== undefined) {
      this.subscribeToSaveResponse(this.pasajeroService.update(pasajero));
    } else {
      this.subscribeToSaveResponse(this.pasajeroService.create(pasajero));
    }
  }

  private createFromForm(): IPasajero {
    return {
      ...new Pasajero(),
      id: this.editForm.get(['id'])!.value,
      idpas: this.editForm.get(['idpas'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      idt: this.editForm.get(['idt'])!.value,
      idpas: this.editForm.get(['idpas'])!.value,
      tiquete: this.editForm.get(['tiquete'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPasajero>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IVuelo[], option: IVuelo): IVuelo {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
