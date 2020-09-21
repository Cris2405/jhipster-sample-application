import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from './aeropuerto.service';

@Component({
  templateUrl: './aeropuerto-delete-dialog.component.html',
})
export class AeropuertoDeleteDialogComponent {
  aeropuerto?: IAeropuerto;

  constructor(
    protected aeropuertoService: AeropuertoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aeropuertoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aeropuertoListModification');
      this.activeModal.close();
    });
  }
}
