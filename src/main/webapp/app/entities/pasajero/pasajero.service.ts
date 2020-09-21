import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPasajero } from 'app/shared/model/pasajero.model';

type EntityResponseType = HttpResponse<IPasajero>;
type EntityArrayResponseType = HttpResponse<IPasajero[]>;

@Injectable({ providedIn: 'root' })
export class PasajeroService {
  public resourceUrl = SERVER_API_URL + 'api/pasajeros';

  constructor(protected http: HttpClient) {}

  create(pasajero: IPasajero): Observable<EntityResponseType> {
    return this.http.post<IPasajero>(this.resourceUrl, pasajero, { observe: 'response' });
  }

  update(pasajero: IPasajero): Observable<EntityResponseType> {
    return this.http.put<IPasajero>(this.resourceUrl, pasajero, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPasajero>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPasajero[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
