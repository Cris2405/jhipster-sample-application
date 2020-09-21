import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPasajero } from 'app/shared/model/pasajero.model';
import { PasajeroService } from './pasajero.service';

@Component({
  templateUrl: './pasajero-delete-dialog.component.html',
})
export class PasajeroDeleteDialogComponent {
  pasajero?: IPasajero;

  constructor(protected pasajeroService: PasajeroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pasajeroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pasajeroListModification');
      this.activeModal.close();
    });
  }
}
