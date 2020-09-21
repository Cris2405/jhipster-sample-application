import { IVuelo } from 'app/shared/model/vuelo.model';
import { ITiquete } from 'app/shared/model/tiquete.model';

export interface IPasajero {
  id?: number;
  idpas?: number;
  nombre?: string;
  identificacion?: string;
  idt?: number;
  idpas?: IVuelo[];
  tiquete?: ITiquete;
}

export class Pasajero implements IPasajero {
  constructor(
    public id?: number,
    public idpas?: number,
    public nombre?: string,
    public identificacion?: string,
    public idt?: number,
    public idpas?: IVuelo[],
    public tiquete?: ITiquete
  ) {}
}
