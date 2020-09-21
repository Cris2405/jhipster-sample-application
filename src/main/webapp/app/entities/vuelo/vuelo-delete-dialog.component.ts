import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from './vuelo.service';

@Component({
  templateUrl: './vuelo-delete-dialog.component.html',
})
export class VueloDeleteDialogComponent {
  vuelo?: IVuelo;

  constructor(protected vueloService: VueloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vueloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vueloListModification');
      this.activeModal.close();
    });
  }
}
