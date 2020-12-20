import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';

type EntityResponseType = HttpResponse<ICreneauHoraire>;
type EntityArrayResponseType = HttpResponse<ICreneauHoraire[]>;

@Injectable({ providedIn: 'root' })
export class CreneauHoraireService {
  public resourceUrl = SERVER_API_URL + 'api/creneau-horaires';

  constructor(protected http: HttpClient) {}

  create(creneauHoraire: ICreneauHoraire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creneauHoraire);
    return this.http
      .post<ICreneauHoraire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(creneauHoraire: ICreneauHoraire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creneauHoraire);
    return this.http
      .put<ICreneauHoraire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICreneauHoraire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICreneauHoraire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(creneauHoraire: ICreneauHoraire): ICreneauHoraire {
    const copy: ICreneauHoraire = Object.assign({}, creneauHoraire, {
      heureDebut: creneauHoraire.heureDebut && creneauHoraire.heureDebut.isValid() ? creneauHoraire.heureDebut.toJSON() : undefined,
      heureFin: creneauHoraire.heureFin && creneauHoraire.heureFin.isValid() ? creneauHoraire.heureFin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureDebut = res.body.heureDebut ? moment(res.body.heureDebut) : undefined;
      res.body.heureFin = res.body.heureFin ? moment(res.body.heureFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((creneauHoraire: ICreneauHoraire) => {
        creneauHoraire.heureDebut = creneauHoraire.heureDebut ? moment(creneauHoraire.heureDebut) : undefined;
        creneauHoraire.heureFin = creneauHoraire.heureFin ? moment(creneauHoraire.heureFin) : undefined;
      });
    }
    return res;
  }
}
