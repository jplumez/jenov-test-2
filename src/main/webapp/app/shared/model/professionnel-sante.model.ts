import { IVaccination } from 'app/shared/model/vaccination.model';
import { ILocalite } from 'app/shared/model/localite.model';
import { ICentre } from 'app/shared/model/centre.model';

export interface IProfessionnelSante {
  id?: number;
  nom?: string;
  vaccinations?: IVaccination[];
  localite?: ILocalite;
  centre?: ICentre;
}

export class ProfessionnelSante implements IProfessionnelSante {
  constructor(
    public id?: number,
    public nom?: string,
    public vaccinations?: IVaccination[],
    public localite?: ILocalite,
    public centre?: ICentre
  ) {}
}
