import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPasajero } from 'app/shared/model/pasajero.model';
import { PasajeroService } from './pasajero.service';
import { PasajeroDeleteDialogComponent } from './pasajero-delete-dialog.component';

@Component({
  selector: 'jhi-pasajero',
  templateUrl: './pasajero.component.html',
})
export class PasajeroComponent implements OnInit, OnDestroy {
  pasajeros?: IPasajero[];
  eventSubscriber?: Subscription;

  constructor(protected pasajeroService: PasajeroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pasajeroService.query().subscribe((res: HttpResponse<IPasajero[]>) => (this.pasajeros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPasajeros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPasajero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPasajeros(): void {
    this.eventSubscriber = this.eventManager.subscribe('pasajeroListModification', () => this.loadAll());
  }

  delete(pasajero: IPasajero): void {
    const modalRef = this.modalService.open(PasajeroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pasajero = pasajero;
  }
}
