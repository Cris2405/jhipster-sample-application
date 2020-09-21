import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from './aeropuerto.service';
import { AeropuertoDeleteDialogComponent } from './aeropuerto-delete-dialog.component';

@Component({
  selector: 'jhi-aeropuerto',
  templateUrl: './aeropuerto.component.html',
})
export class AeropuertoComponent implements OnInit, OnDestroy {
  aeropuertos?: IAeropuerto[];
  eventSubscriber?: Subscription;

  constructor(protected aeropuertoService: AeropuertoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.aeropuertoService.query().subscribe((res: HttpResponse<IAeropuerto[]>) => (this.aeropuertos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAeropuertos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAeropuerto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAeropuertos(): void {
    this.eventSubscriber = this.eventManager.subscribe('aeropuertoListModification', () => this.loadAll());
  }

  delete(aeropuerto: IAeropuerto): void {
    const modalRef = this.modalService.open(AeropuertoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aeropuerto = aeropuerto;
  }
}
