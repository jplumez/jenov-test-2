import { Moment } from 'moment';
import { IVaccination } from 'app/shared/model/vaccination.model';
import { ICentre } from 'app/shared/model/centre.model';

export interface ICreneauHoraire {
  id?: number;
  capacite?: number;
  heureDebut?: Moment;
  heureFin?: Moment;
  vaccinations?: IVaccination[];
  centre?: ICentre;
}

export class CreneauHoraire implements ICreneauHoraire {
  constructor(
    public id?: number,
    public capacite?: number,
    public heureDebut?: Moment,
    public heureFin?: Moment,
    public vaccinations?: IVaccination[],
    public centre?: ICentre
  ) {}
}
