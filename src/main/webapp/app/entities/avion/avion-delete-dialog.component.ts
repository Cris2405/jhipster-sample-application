import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvion } from 'app/shared/model/avion.model';
import { AvionService } from './avion.service';

@Component({
  templateUrl: './avion-delete-dialog.component.html',
})
export class AvionDeleteDialogComponent {
  avion?: IAvion;

  constructor(protected avionService: AvionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avionListModification');
      this.activeModal.close();
    });
  }
}
