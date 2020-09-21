import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAeropuerto } from 'app/shared/model/aeropuerto.model';

type EntityResponseType = HttpResponse<IAeropuerto>;
type EntityArrayResponseType = HttpResponse<IAeropuerto[]>;

@Injectable({ providedIn: 'root' })
export class AeropuertoService {
  public resourceUrl = SERVER_API_URL + 'api/aeropuertos';

  constructor(protected http: HttpClient) {}

  create(aeropuerto: IAeropuerto): Observable<EntityResponseType> {
    return this.http.post<IAeropuerto>(this.resourceUrl, aeropuerto, { observe: 'response' });
  }

  update(aeropuerto: IAeropuerto): Observable<EntityResponseType> {
    return this.http.put<IAeropuerto>(this.resourceUrl, aeropuerto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAeropuerto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAeropuerto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
