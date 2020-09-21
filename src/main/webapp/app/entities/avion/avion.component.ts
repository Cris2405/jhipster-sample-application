import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvion } from 'app/shared/model/avion.model';
import { AvionService } from './avion.service';
import { AvionDeleteDialogComponent } from './avion-delete-dialog.component';

@Component({
  selector: 'jhi-avion',
  templateUrl: './avion.component.html',
})
export class AvionComponent implements OnInit, OnDestroy {
  avions?: IAvion[];
  eventSubscriber?: Subscription;

  constructor(protected avionService: AvionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.avionService.query().subscribe((res: HttpResponse<IAvion[]>) => (this.avions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvions(): void {
    this.eventSubscriber = this.eventManager.subscribe('avionListModification', () => this.loadAll());
  }

  delete(avion: IAvion): void {
    const modalRef = this.modalService.open(AvionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avion = avion;
  }
}
