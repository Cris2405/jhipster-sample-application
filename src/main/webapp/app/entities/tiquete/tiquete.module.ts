import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FirstAppSharedModule } from 'app/shared/shared.module';
import { TiqueteComponent } from './tiquete.component';
import { TiqueteDetailComponent } from './tiquete-detail.component';
import { TiqueteUpdateComponent } from './tiquete-update.component';
import { TiqueteDeleteDialogComponent } from './tiquete-delete-dialog.component';
import { tiqueteRoute } from './tiquete.route';

@NgModule({
  imports: [FirstAppSharedModule, RouterModule.forChild(tiqueteRoute)],
  declarations: [TiqueteComponent, TiqueteDetailComponent, TiqueteUpdateComponent, TiqueteDeleteDialogComponent],
  entryComponents: [TiqueteDeleteDialogComponent],
})
export class FirstAppTiqueteModule {}
