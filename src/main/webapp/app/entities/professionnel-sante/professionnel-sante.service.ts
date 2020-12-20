import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';

type EntityResponseType = HttpResponse<IProfessionnelSante>;
type EntityArrayResponseType = HttpResponse<IProfessionnelSante[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionnelSanteService {
  public resourceUrl = SERVER_API_URL + 'api/professionnel-santes';

  constructor(protected http: HttpClient) {}

  create(professionnelSante: IProfessionnelSante): Observable<EntityResponseType> {
    return this.http.post<IProfessionnelSante>(this.resourceUrl, professionnelSante, { observe: 'response' });
  }

  update(professionnelSante: IProfessionnelSante): Observable<EntityResponseType> {
    return this.http.put<IProfessionnelSante>(this.resourceUrl, professionnelSante, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfessionnelSante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfessionnelSante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
