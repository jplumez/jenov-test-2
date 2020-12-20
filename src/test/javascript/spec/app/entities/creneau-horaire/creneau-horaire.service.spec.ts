import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CreneauHoraireService } from 'app/entities/creneau-horaire/creneau-horaire.service';
import { ICreneauHoraire, CreneauHoraire } from 'app/shared/model/creneau-horaire.model';

describe('Service Tests', () => {
  describe('CreneauHoraire Service', () => {
    let injector: TestBed;
    let service: CreneauHoraireService;
    let httpMock: HttpTestingController;
    let elemDefault: ICreneauHoraire;
    let expectedResult: ICreneauHoraire | ICreneauHoraire[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CreneauHoraireService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CreneauHoraire(0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureDebut: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CreneauHoraire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureDebut: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
          },
          returnedFromService
        );

        service.create(new CreneauHoraire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CreneauHoraire', () => {
        const returnedFromService = Object.assign(
          {
            capacite: 1,
            heureDebut: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CreneauHoraire', () => {
        const returnedFromService = Object.assign(
          {
            capacite: 1,
            heureDebut: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CreneauHoraire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
