import { IPasajero } from 'app/shared/model/pasajero.model';

export interface ITiquete {
  id?: number;
  idt?: number;
  asiento?: string;
  fecha?: string;
  pasajeros?: IPasajero[];
}

export class Tiquete implements ITiquete {
  constructor(public id?: number, public idt?: number, public asiento?: string, public fecha?: string, public pasajeros?: IPasajero[]) {}
}
