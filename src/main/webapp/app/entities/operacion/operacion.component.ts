import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperacion } from 'app/shared/model/operacion.model';
import { OperacionService } from './operacion.service';
import { OperacionDeleteDialogComponent } from './operacion-delete-dialog.component';

@Component({
  selector: 'jhi-operacion',
  templateUrl: './operacion.component.html',
})
export class OperacionComponent implements OnInit, OnDestroy {
  operacions?: IOperacion[];
  eventSubscriber?: Subscription;

  constructor(protected operacionService: OperacionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.operacionService.query().subscribe((res: HttpResponse<IOperacion[]>) => (this.operacions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOperacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOperacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOperacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('operacionListModification', () => this.loadAll());
  }

  delete(operacion: IOperacion): void {
    const modalRef = this.modalService.open(OperacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.operacion = operacion;
  }
}
