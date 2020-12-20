import { Moment } from 'moment';
import { IVaccination } from 'app/shared/model/vaccination.model';
import { ICentre } from 'app/shared/model/centre.model';

export interface ILotVaccin {
  id?: number;
  stockInitial?: number;
  stockActuel?: number;
  heureInventaire?: Moment;
  vaccinations?: IVaccination[];
  centre?: ICentre;
}

export class LotVaccin implements ILotVaccin {
  constructor(
    public id?: number,
    public stockInitial?: number,
    public stockActuel?: number,
    public heureInventaire?: Moment,
    public vaccinations?: IVaccination[],
    public centre?: ICentre
  ) {}
}
