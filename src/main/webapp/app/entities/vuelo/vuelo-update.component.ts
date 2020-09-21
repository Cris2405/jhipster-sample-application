import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVuelo, Vuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from './vuelo.service';
import { IAvion } from 'app/shared/model/avion.model';
import { AvionService } from 'app/entities/avion/avion.service';
import { IAeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from 'app/entities/aeropuerto/aeropuerto.service';

type SelectableEntity = IAvion | IAeropuerto;

@Component({
  selector: 'jhi-vuelo-update',
  templateUrl: './vuelo-update.component.html',
})
export class VueloUpdateComponent implements OnInit {
  isSaving = false;
  avions: IAvion[] = [];
  aeropuertos: IAeropuerto[] = [];

  editForm = this.fb.group({
    id: [],
    idvuelo: [],
    origen: [],
    destino: [],
    idpas: [],
    avion: [],
    aeropuerto: [],
  });

  constructor(
    protected vueloService: VueloService,
    protected avionService: AvionService,
    protected aeropuertoService: AeropuertoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vuelo }) => {
      this.updateForm(vuelo);

      this.avionService.query().subscribe((res: HttpResponse<IAvion[]>) => (this.avions = res.body || []));

      this.aeropuertoService.query().subscribe((res: HttpResponse<IAeropuerto[]>) => (this.aeropuertos = res.body || []));
    });
  }

  updateForm(vuelo: IVuelo): void {
    this.editForm.patchValue({
      id: vuelo.id,
      idvuelo: vuelo.idvuelo,
      origen: vuelo.origen,
      destino: vuelo.destino,
      idpas: vuelo.idpas,
      avion: vuelo.avion,
      aeropuerto: vuelo.aeropuerto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vuelo = this.createFromForm();
    if (vuelo.id !== undefined) {
      this.subscribeToSaveResponse(this.vueloService.update(vuelo));
    } else {
      this.subscribeToSaveResponse(this.vueloService.create(vuelo));
    }
  }

  private createFromForm(): IVuelo {
    return {
      ...new Vuelo(),
      id: this.editForm.get(['id'])!.value,
      idvuelo: this.editForm.get(['idvuelo'])!.value,
      origen: this.editForm.get(['origen'])!.value,
      destino: this.editForm.get(['destino'])!.value,
      idpas: this.editForm.get(['idpas'])!.value,
      avion: this.editForm.get(['avion'])!.value,
      aeropuerto: this.editForm.get(['aeropuerto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVuelo>>): void {
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
}
