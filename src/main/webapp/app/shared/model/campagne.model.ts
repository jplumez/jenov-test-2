import { Moment } from 'moment';
import { ICentre } from 'app/shared/model/centre.model';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';

export interface ICampagne {
  id?: number;
  nom?: string;
  description?: string;
  debut?: Moment;
  fin?: Moment;
  nbJoursRappel?: number;
  remarques?: string;
  centre?: ICentre;
  questionnaires?: IQuestionnaire[];
}

export class Campagne implements ICampagne {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public debut?: Moment,
    public fin?: Moment,
    public nbJoursRappel?: number,
    public remarques?: string,
    public centre?: ICentre,
    public questionnaires?: IQuestionnaire[]
  ) {}
}
