import { ICampagne } from 'app/shared/model/campagne.model';

export interface IQuestionnaire {
  id?: number;
  question?: string;
  campagne?: ICampagne;
}

export class Questionnaire implements IQuestionnaire {
  constructor(public id?: number, public question?: string, public campagne?: ICampagne) {}
}
