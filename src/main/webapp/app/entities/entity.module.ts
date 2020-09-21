import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vuelo',
        loadChildren: () => import('./vuelo/vuelo.module').then(m => m.FirstAppVueloModule),
      },
      {
        path: 'avion',
        loadChildren: () => import('./avion/avion.module').then(m => m.FirstAppAvionModule),
      },
      {
        path: 'tiquete',
        loadChildren: () => import('./tiquete/tiquete.module').then(m => m.FirstAppTiqueteModule),
      },
      {
        path: 'operacion',
        loadChildren: () => import('./operacion/operacion.module').then(m => m.FirstAppOperacionModule),
      },
      {
        path: 'aeropuerto',
        loadChildren: () => import('./aeropuerto/aeropuerto.module').then(m => m.FirstAppAeropuertoModule),
      },
      {
        path: 'pasajero',
        loadChildren: () => import('./pasajero/pasajero.module').then(m => m.FirstAppPasajeroModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FirstAppEntityModule {}
