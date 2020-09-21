import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvion, Avion } from 'app/shared/model/avion.model';
import { AvionService } from './avion.service';
import { IAeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from 'app/entities/aeropuerto/aeropuerto.service';

@Component({
  selector: 'jhi-avion-update',
  templateUrl: './avion-update.component.html',
})
export class AvionUpdateComponent implements OnInit {
  isSaving = false;
  aeropuertos: IAeropuerto[] = [];

  editForm = this.fb.group({
    id: [],
    idavion: [],
    modelo: [],
    capacidad: [],
    idaero: [],
    aeropuerto: [],
  });

  constructor(
    protected avionService: AvionService,
    protected aeropuertoService: AeropuertoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avion }) => {
      this.updateForm(avion);

      this.aeropuertoService.query().subscribe((res: HttpResponse<IAeropuerto[]>) => (this.aeropuertos = res.body || []));
    });
  }

  updateForm(avion: IAvion): void {
    this.editForm.patchValue({
      id: avion.id,
      idavion: avion.idavion,
      modelo: avion.modelo,
      capacidad: avion.capacidad,
      idaero: avion.idaero,
      aeropuerto: avion.aeropuerto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avion = this.createFromForm();
    if (avion.id !== undefined) {
      this.subscribeToSaveResponse(this.avionService.update(avion));
    } else {
      this.subscribeToSaveResponse(this.avionService.create(avion));
    }
  }

  private createFromForm(): IAvion {
    return {
      ...new Avion(),
      id: this.editForm.get(['id'])!.value,
      idavion: this.editForm.get(['idavion'])!.value,
      modelo: this.editForm.get(['modelo'])!.value,
      capacidad: this.editForm.get(['capacidad'])!.value,
      idaero: this.editForm.get(['idaero'])!.value,
      aeropuerto: this.editForm.get(['aeropuerto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvion>>): void {
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

  trackById(index: number, item: IAeropuerto): any {
    return item.id;
  }
}
