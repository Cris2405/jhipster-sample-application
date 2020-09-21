import { IVuelo } from 'app/shared/model/vuelo.model';
import { IOperacion } from 'app/shared/model/operacion.model';
import { IAeropuerto } from 'app/shared/model/aeropuerto.model';

export interface IAvion {
  id?: number;
  idavion?: number;
  modelo?: string;
  capacidad?: number;
  idaero?: string;
  vuelos?: IVuelo[];
  operacions?: IOperacion[];
  aeropuerto?: IAeropuerto;
}

export class Avion implements IAvion {
  constructor(
    public id?: number,
    public idavion?: number,
    public modelo?: string,
    public capacidad?: number,
    public idaero?: string,
    public vuelos?: IVuelo[],
    public operacions?: IOperacion[],
    public aeropuerto?: IAeropuerto
  ) {}
}
