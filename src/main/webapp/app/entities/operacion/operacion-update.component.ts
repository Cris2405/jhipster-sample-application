import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOperacion, Operacion } from 'app/shared/model/operacion.model';
import { OperacionService } from './operacion.service';
import { IAvion } from 'app/shared/model/avion.model';
import { AvionService } from 'app/entities/avion/avion.service';
import { IVuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from 'app/entities/vuelo/vuelo.service';

type SelectableEntity = IAvion | IVuelo;

@Component({
  selector: 'jhi-operacion-update',
  templateUrl: './operacion-update.component.html',
})
export class OperacionUpdateComponent implements OnInit {
  isSaving = false;
  avions: IAvion[] = [];
  vuelos: IVuelo[] = [];

  editForm = this.fb.group({
    id: [],
    idop: [],
    despegue: [],
    aterrizaje: [],
    fecha: [],
    idavion: [],
    avion: [],
    vuelo: [],
  });

  constructor(
    protected operacionService: OperacionService,
    protected avionService: AvionService,
    protected vueloService: VueloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operacion }) => {
      this.updateForm(operacion);

      this.avionService.query().subscribe((res: HttpResponse<IAvion[]>) => (this.avions = res.body || []));

      this.vueloService.query().subscribe((res: HttpResponse<IVuelo[]>) => (this.vuelos = res.body || []));
    });
  }

  updateForm(operacion: IOperacion): void {
    this.editForm.patchValue({
      id: operacion.id,
      idop: operacion.idop,
      despegue: operacion.despegue,
      aterrizaje: operacion.aterrizaje,
      fecha: operacion.fecha,
      idavion: operacion.idavion,
      avion: operacion.avion,
      vuelo: operacion.vuelo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operacion = this.createFromForm();
    if (operacion.id !== undefined) {
      this.subscribeToSaveResponse(this.operacionService.update(operacion));
    } else {
      this.subscribeToSaveResponse(this.operacionService.create(operacion));
    }
  }

  private createFromForm(): IOperacion {
    return {
      ...new Operacion(),
      id: this.editForm.get(['id'])!.value,
      idop: this.editForm.get(['idop'])!.value,
      despegue: this.editForm.get(['despegue'])!.value,
      aterrizaje: this.editForm.get(['aterrizaje'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      idavion: this.editForm.get(['idavion'])!.value,
      avion: this.editForm.get(['avion'])!.value,
      vuelo: this.editForm.get(['vuelo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperacion>>): void {
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
