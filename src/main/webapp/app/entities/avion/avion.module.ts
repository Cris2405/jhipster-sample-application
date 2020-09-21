import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { AvionComponent } from './avion.component';
import { AvionDetailComponent } from './avion-detail.component';
import { AvionUpdateComponent } from './avion-update.component';
import { AvionDeleteDialogComponent } from './avion-delete-dialog.component';
import { avionRoute } from './avion.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(avionRoute)],
  declarations: [AvionComponent, AvionDetailComponent, AvionUpdateComponent, AvionDeleteDialogComponent],
  entryComponents: [AvionDeleteDialogComponent],
})
export class FirstAppAvionModule {}
