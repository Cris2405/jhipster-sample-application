import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAeropuerto, Aeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from './aeropuerto.service';

@Component({
  selector: 'jhi-aeropuerto-update',
  templateUrl: './aeropuerto-update.component.html',
})
export class AeropuertoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idaero: [],
    nombre: [],
    pais: [],
    ciudad: [],
    direccion: [],
  });

  constructor(protected aeropuertoService: AeropuertoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeropuerto }) => {
      this.updateForm(aeropuerto);
    });
  }

  updateForm(aeropuerto: IAeropuerto): void {
    this.editForm.patchValue({
      id: aeropuerto.id,
      idaero: aeropuerto.idaero,
      nombre: aeropuerto.nombre,
      pais: aeropuerto.pais,
      ciudad: aeropuerto.ciudad,
      direccion: aeropuerto.direccion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aeropuerto = this.createFromForm();
    if (aeropuerto.id !== undefined) {
      this.subscribeToSaveResponse(this.aeropuertoService.update(aeropuerto));
    } else {
      this.subscribeToSaveResponse(this.aeropuertoService.create(aeropuerto));
    }
  }

  private createFromForm(): IAeropuerto {
    return {
      ...new Aeropuerto(),
      id: this.editForm.get(['id'])!.value,
      idaero: this.editForm.get(['idaero'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      ciudad: this.editForm.get(['ciudad'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAeropuerto>>): void {
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
}
