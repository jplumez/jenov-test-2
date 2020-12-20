import { Moment } from 'moment';
import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';
import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IVaccination {
  id?: number;
  dateCreation?: Moment;
  dateRendezVous?: Moment;
  dateVaccin?: Moment;
  creneau?: ICreneauHoraire;
  stockVaccin?: ILotVaccin;
  executant?: IProfessionnelSante;
  patients?: IPatient[];
}

export class Vaccination implements IVaccination {
  constructor(
    public id?: number,
    public dateCreation?: Moment,
    public dateRendezVous?: Moment,
    public dateVaccin?: Moment,
    public creneau?: ICreneauHoraire,
    public stockVaccin?: ILotVaccin,
    public executant?: IProfessionnelSante,
    public patients?: IPatient[]
  ) {}
}
