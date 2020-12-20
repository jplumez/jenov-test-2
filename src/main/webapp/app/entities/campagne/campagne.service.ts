import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICampagne } from 'app/shared/model/campagne.model';

type EntityResponseType = HttpResponse<ICampagne>;
type EntityArrayResponseType = HttpResponse<ICampagne[]>;

@Injectable({ providedIn: 'root' })
export class CampagneService {
  public resourceUrl = SERVER_API_URL + 'api/campagnes';

  constructor(protected http: HttpClient) {}

  create(campagne: ICampagne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(campagne);
    return this.http
      .post<ICampagne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(campagne: ICampagne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(campagne);
    return this.http
      .put<ICampagne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICampagne>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICampagne[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(campagne: ICampagne): ICampagne {
    const copy: ICampagne = Object.assign({}, campagne, {
      debut: campagne.debut && campagne.debut.isValid() ? campagne.debut.format(DATE_FORMAT) : undefined,
      fin: campagne.fin && campagne.fin.isValid() ? campagne.fin.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.debut = res.body.debut ? moment(res.body.debut) : undefined;
      res.body.fin = res.body.fin ? moment(res.body.fin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((campagne: ICampagne) => {
        campagne.debut = campagne.debut ? moment(campagne.debut) : undefined;
        campagne.fin = campagne.fin ? moment(campagne.fin) : undefined;
      });
    }
    return res;
  }
}
