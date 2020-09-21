import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { PasajeroComponent } from './pasajero.component';
import { PasajeroDetailComponent } from './pasajero-detail.component';
import { PasajeroUpdateComponent } from './pasajero-update.component';
import { PasajeroDeleteDialogComponent } from './pasajero-delete-dialog.component';
import { pasajeroRoute } from './pasajero.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(pasajeroRoute)],
  declarations: [PasajeroComponent, PasajeroDetailComponent, PasajeroUpdateComponent, PasajeroDeleteDialogComponent],
  entryComponents: [PasajeroDeleteDialogComponent],
})
export class FirstAppPasajeroModule {}
