import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITiquete } from 'app/shared/model/tiquete.model';

@Component({
  selector: 'jhi-tiquete-detail',
  templateUrl: './tiquete-detail.component.html',
})
export class TiqueteDetailComponent implements OnInit {
  tiquete: ITiquete | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiquete }) => (this.tiquete = tiquete));
  }

  previousState(): void {
    window.history.back();
  }
}
