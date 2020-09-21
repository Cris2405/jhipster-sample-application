import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { OperacionComponent } from './operacion.component';
import { OperacionDetailComponent } from './operacion-detail.component';
import { OperacionUpdateComponent } from './operacion-update.component';
import { OperacionDeleteDialogComponent } from './operacion-delete-dialog.component';
import { operacionRoute } from './operacion.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(operacionRoute)],
  declarations: [OperacionComponent, OperacionDetailComponent, OperacionUpdateComponent, OperacionDeleteDialogComponent],
  entryComponents: [OperacionDeleteDialogComponent],
})
export class FirstAppOperacionModule {}
