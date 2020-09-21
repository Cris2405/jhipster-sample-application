import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { AeropuertoComponent } from './aeropuerto.component';
import { AeropuertoDetailComponent } from './aeropuerto-detail.component';
import { AeropuertoUpdateComponent } from './aeropuerto-update.component';
import { AeropuertoDeleteDialogComponent } from './aeropuerto-delete-dialog.component';
import { aeropuertoRoute } from './aeropuerto.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(aeropuertoRoute)],
  declarations: [AeropuertoComponent, AeropuertoDetailComponent, AeropuertoUpdateComponent, AeropuertoDeleteDialogComponent],
  entryComponents: [AeropuertoDeleteDialogComponent],
})
export class FirstAppAeropuertoModule {}
