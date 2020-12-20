export interface ILocalite {
  id?: number;
  npa?: string;
  commune?: string;
}

export class Localite implements ILocalite {
  constructor(public id?: number, public npa?: string, public commune?: string) {}
}
