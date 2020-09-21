import { IOperacion } from 'app/shared/model/operacion.model';
import { IAvion } from 'app/shared/model/avion.model';
import { IAeropuerto } from 'app/shared/model/aeropuerto.model';
import { IPasajero } from 'app/shared/model/pasajero.model';

export interface IVuelo {
  id?: number;
  idvuelo?: number;
  origen?: string;
  destino?: string;
  idpas?: number;
  operacions?: IOperacion[];
  avion?: IAvion;
  aeropuerto?: IAeropuerto;
  idvuelos?: IPasajero[];
}

export class Vuelo implements IVuelo {
  constructor(
    public id?: number,
    public idvuelo?: number,
    public origen?: string,
    public destino?: string,
    public idpas?: number,
    public operacions?: IOperacion[],
    public avion?: IAvion,
    public aeropuerto?: IAeropuerto,
    public idvuelos?: IPasajero[]
  ) {}
}
