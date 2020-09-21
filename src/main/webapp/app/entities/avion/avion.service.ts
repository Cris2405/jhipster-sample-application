import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvion } from 'app/shared/model/avion.model';

type EntityResponseType = HttpResponse<IAvion>;
type EntityArrayResponseType = HttpResponse<IAvion[]>;

@Injectable({ providedIn: 'root' })
export class AvionService {
  public resourceUrl = SERVER_API_URL + 'api/avions';

  constructor(protected http: HttpClient) {}

  create(avion: IAvion): Observable<EntityResponseType> {
    return this.http.post<IAvion>(this.resourceUrl, avion, { observe: 'response' });
  }

  update(avion: IAvion): Observable<EntityResponseType> {
    return this.http.put<IAvion>(this.resourceUrl, avion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
