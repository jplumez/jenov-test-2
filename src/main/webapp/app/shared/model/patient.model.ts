import { Moment } from 'moment';
import { IVaccination } from 'app/shared/model/vaccination.model';
import { ILocalite } from 'app/shared/model/localite.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';
import { GroupeRisque } from 'app/shared/model/enumerations/groupe-risque.model';

export interface IPatient {
  id?: number;
  noAvs?: string;
  dateNaissance?: Moment;
  nom?: string;
  prenom?: string;
  sexe?: Sexe;
  adresse?: string;
  telephone?: string;
  email?: string;
  numeroAssure?: string;
  groupeRisque?: GroupeRisque;
  vaccination?: IVaccination;
  localite?: ILocalite;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public noAvs?: string,
    public dateNaissance?: Moment,
    public nom?: string,
    public prenom?: string,
    public sexe?: Sexe,
    public adresse?: string,
    public telephone?: string,
    public email?: string,
    public numeroAssure?: string,
    public groupeRisque?: GroupeRisque,
    public vaccination?: IVaccination,
    public localite?: ILocalite
  ) {}
}
