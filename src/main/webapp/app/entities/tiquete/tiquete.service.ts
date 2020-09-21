import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITiquete } from 'app/shared/model/tiquete.model';

type EntityResponseType = HttpResponse<ITiquete>;
type EntityArrayResponseType = HttpResponse<ITiquete[]>;

@Injectable({ providedIn: 'root' })
export class TiqueteService {
  public resourceUrl = SERVER_API_URL + 'api/tiquetes';

  constructor(protected http: HttpClient) {}

  create(tiquete: ITiquete): Observable<EntityResponseType> {
    return this.http.post<ITiquete>(this.resourceUrl, tiquete, { observe: 'response' });
  }

  update(tiquete: ITiquete): Observable<EntityResponseType> {
    return this.http.put<ITiquete>(this.resourceUrl, tiquete, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITiquete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITiquete[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
