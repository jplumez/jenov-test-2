import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VaccinationService } from 'app/entities/vaccination/vaccination.service';
import { IVaccination, Vaccination } from 'app/shared/model/vaccination.model';

describe('Service Tests', () => {
  describe('Vaccination Service', () => {
    let injector: TestBed;
    let service: VaccinationService;
    let httpMock: HttpTestingController;
    let elemDefault: IVaccination;
    let expectedResult: IVaccination | IVaccination[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VaccinationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vaccination(0, currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            dateRendezVous: currentDate.format(DATE_TIME_FORMAT),
            dateVaccin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Vaccination', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            dateRendezVous: currentDate.format(DATE_TIME_FORMAT),
            dateVaccin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateRendezVous: currentDate,
            dateVaccin: currentDate,
          },
          returnedFromService
        );

        service.create(new Vaccination()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vaccination', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            dateRendezVous: currentDate.format(DATE_TIME_FORMAT),
            dateVaccin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateRendezVous: currentDate,
            dateVaccin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Vaccination', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            dateRendezVous: currentDate.format(DATE_TIME_FORMAT),
            dateVaccin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateRendezVous: currentDate,
            dateVaccin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vaccination', () => {
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
