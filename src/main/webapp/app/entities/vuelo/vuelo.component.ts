import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from './vuelo.service';
import { VueloDeleteDialogComponent } from './vuelo-delete-dialog.component';

@Component({
  selector: 'jhi-vuelo',
  templateUrl: './vuelo.component.html',
})
export class VueloComponent implements OnInit, OnDestroy {
  vuelos?: IVuelo[];
  eventSubscriber?: Subscription;

  constructor(protected vueloService: VueloService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.vueloService.query().subscribe((res: HttpResponse<IVuelo[]>) => (this.vuelos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVuelos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVuelo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVuelos(): void {
    this.eventSubscriber = this.eventManager.subscribe('vueloListModification', () => this.loadAll());
  }

  delete(vuelo: IVuelo): void {
    const modalRef = this.modalService.open(VueloDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vuelo = vuelo;
  }
}
