import { IAvion } from 'app/shared/model/avion.model';
import { IVuelo } from 'app/shared/model/vuelo.model';

export interface IOperacion {
  id?: number;
  idop?: number;
  despegue?: string;
  aterrizaje?: string;
  fecha?: string;
  idavion?: number;
  avion?: IAvion;
  vuelo?: IVuelo;
}

export class Operacion implements IOperacion {
  constructor(
    public id?: number,
    public idop?: number,
    public despegue?: string,
    public aterrizaje?: string,
    public fecha?: string,
    public idavion?: number,
    public avion?: IAvion,
    public vuelo?: IVuelo
  ) {}
}
