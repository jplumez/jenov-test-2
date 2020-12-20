import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';

type EntityResponseType = HttpResponse<ILotVaccin>;
type EntityArrayResponseType = HttpResponse<ILotVaccin[]>;

@Injectable({ providedIn: 'root' })
export class LotVaccinService {
  public resourceUrl = SERVER_API_URL + 'api/lot-vaccins';

  constructor(protected http: HttpClient) {}

  create(lotVaccin: ILotVaccin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lotVaccin);
    return this.http
      .post<ILotVaccin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lotVaccin: ILotVaccin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lotVaccin);
    return this.http
      .put<ILotVaccin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILotVaccin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILotVaccin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lotVaccin: ILotVaccin): ILotVaccin {
    const copy: ILotVaccin = Object.assign({}, lotVaccin, {
      heureInventaire: lotVaccin.heureInventaire && lotVaccin.heureInventaire.isValid() ? lotVaccin.heureInventaire.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureInventaire = res.body.heureInventaire ? moment(res.body.heureInventaire) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lotVaccin: ILotVaccin) => {
        lotVaccin.heureInventaire = lotVaccin.heureInventaire ? moment(lotVaccin.heureInventaire) : undefined;
      });
    }
    return res;
  }
}
