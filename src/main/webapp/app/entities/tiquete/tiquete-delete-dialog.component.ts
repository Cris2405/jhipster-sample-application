import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiquete } from 'app/shared/model/tiquete.model';
import { TiqueteService } from './tiquete.service';

@Component({
  templateUrl: './tiquete-delete-dialog.component.html',
})
export class TiqueteDeleteDialogComponent {
  tiquete?: ITiquete;

  constructor(protected tiqueteService: TiqueteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiqueteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tiqueteListModification');
      this.activeModal.close();
    });
  }
}
