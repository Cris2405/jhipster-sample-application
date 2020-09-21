import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { VueloComponent } from './vuelo.component';
import { VueloDetailComponent } from './vuelo-detail.component';
import { VueloUpdateComponent } from './vuelo-update.component';
import { VueloDeleteDialogComponent } from './vuelo-delete-dialog.component';
import { vueloRoute } from './vuelo.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(vueloRoute)],
  declarations: [VueloComponent, VueloDetailComponent, VueloUpdateComponent, VueloDeleteDialogComponent],
  entryComponents: [VueloDeleteDialogComponent],
})
export class FirstAppVueloModule {}
