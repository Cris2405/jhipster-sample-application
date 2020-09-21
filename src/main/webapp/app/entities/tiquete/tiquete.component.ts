import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITiquete } from 'app/shared/model/tiquete.model';
import { TiqueteService } from './tiquete.service';
import { TiqueteDeleteDialogComponent } from './tiquete-delete-dialog.component';

@Component({
  selector: 'jhi-tiquete',
  templateUrl: './tiquete.component.html',
})
export class TiqueteComponent implements OnInit, OnDestroy {
  tiquetes?: ITiquete[];
  eventSubscriber?: Subscription;

  constructor(protected tiqueteService: TiqueteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tiqueteService.query().subscribe((res: HttpResponse<ITiquete[]>) => (this.tiquetes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTiquetes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITiquete): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTiquetes(): void {
    this.eventSubscriber = this.eventManager.subscribe('tiqueteListModification', () => this.loadAll());
  }

  delete(tiquete: ITiquete): void {
    const modalRef = this.modalService.open(TiqueteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tiquete = tiquete;
  }
}
