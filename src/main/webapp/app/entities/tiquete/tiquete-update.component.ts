import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITiquete, Tiquete } from 'app/shared/model/tiquete.model';
import { TiqueteService } from './tiquete.service';

@Component({
  selector: 'jhi-tiquete-update',
  templateUrl: './tiquete-update.component.html',
})
export class TiqueteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idt: [],
    asiento: [],
    fecha: [],
  });

  constructor(protected tiqueteService: TiqueteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiquete }) => {
      this.updateForm(tiquete);
    });
  }

  updateForm(tiquete: ITiquete): void {
    this.editForm.patchValue({
      id: tiquete.id,
      idt: tiquete.idt,
      asiento: tiquete.asiento,
      fecha: tiquete.fecha,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tiquete = this.createFromForm();
    if (tiquete.id !== undefined) {
      this.subscribeToSaveResponse(this.tiqueteService.update(tiquete));
    } else {
      this.subscribeToSaveResponse(this.tiqueteService.create(tiquete));
    }
  }

  private createFromForm(): ITiquete {
    return {
      ...new Tiquete(),
      id: this.editForm.get(['id'])!.value,
      idt: this.editForm.get(['idt'])!.value,
      asiento: this.editForm.get(['asiento'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITiquete>>): void {
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
