import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';
import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ICampagne } from 'app/shared/model/campagne.model';

export interface ICentre {
  id?: number;
  code?: string;
  localite?: string;
  description?: string;
  creneauHoraires?: ICreneauHoraire[];
  lotVaccins?: ILotVaccin[];
  professionnelSantes?: IProfessionnelSante[];
  campagnes?: ICampagne[];
}

export class Centre implements ICentre {
  constructor(
    public id?: number,
    public code?: string,
    public localite?: string,
    public description?: string,
    public creneauHoraires?: ICreneauHoraire[],
    public lotVaccins?: ILotVaccin[],
    public professionnelSantes?: IProfessionnelSante[],
    public campagnes?: ICampagne[]
  ) {}
}
