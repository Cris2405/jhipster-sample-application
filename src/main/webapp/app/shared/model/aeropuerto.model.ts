import { IVuelo } from 'app/shared/model/vuelo.model';
import { IAvion } from 'app/shared/model/avion.model';

export interface IAeropuerto {
  id?: number;
  idaero?: number;
  nombre?: string;
  pais?: string;
  ciudad?: string;
  direccion?: string;
  vuelos?: IVuelo[];
  avions?: IAvion[];
}

export class Aeropuerto implements IAeropuerto {
  constructor(
    public id?: number,
    public idaero?: number,
    public nombre?: string,
    public pais?: string,
    public ciudad?: string,
    public direccion?: string,
    public vuelos?: IVuelo[],
    public avions?: IAvion[]
  ) {}
}
