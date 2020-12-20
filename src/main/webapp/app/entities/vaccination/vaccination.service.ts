import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVaccination } from 'app/shared/model/vaccination.model';

type EntityResponseType = HttpResponse<IVaccination>;
type EntityArrayResponseType = HttpResponse<IVaccination[]>;

@Injectable({ providedIn: 'root' })
export class VaccinationService {
  public resourceUrl = SERVER_API_URL + 'api/vaccinations';

  constructor(protected http: HttpClient) {}

  create(vaccination: IVaccination): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vaccination);
    return this.http
      .post<IVaccination>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vaccination: IVaccination): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vaccination);
    return this.http
      .put<IVaccination>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVaccination>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVaccination[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vaccination: IVaccination): IVaccination {
    const copy: IVaccination = Object.assign({}, vaccination, {
      dateCreation: vaccination.dateCreation && vaccination.dateCreation.isValid() ? vaccination.dateCreation.toJSON() : undefined,
      dateRendezVous: vaccination.dateRendezVous && vaccination.dateRendezVous.isValid() ? vaccination.dateRendezVous.toJSON() : undefined,
      dateVaccin: vaccination.dateVaccin && vaccination.dateVaccin.isValid() ? vaccination.dateVaccin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateRendezVous = res.body.dateRendezVous ? moment(res.body.dateRendezVous) : undefined;
      res.body.dateVaccin = res.body.dateVaccin ? moment(res.body.dateVaccin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vaccination: IVaccination) => {
        vaccination.dateCreation = vaccination.dateCreation ? moment(vaccination.dateCreation) : undefined;
        vaccination.dateRendezVous = vaccination.dateRendezVous ? moment(vaccination.dateRendezVous) : undefined;
        vaccination.dateVaccin = vaccination.dateVaccin ? moment(vaccination.dateVaccin) : undefined;
      });
    }
    return res;
  }
}
