import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LotVaccinService } from 'app/entities/lot-vaccin/lot-vaccin.service';
import { ILotVaccin, LotVaccin } from 'app/shared/model/lot-vaccin.model';

describe('Service Tests', () => {
  describe('LotVaccin Service', () => {
    let injector: TestBed;
    let service: LotVaccinService;
    let httpMock: HttpTestingController;
    let elemDefault: ILotVaccin;
    let expectedResult: ILotVaccin | ILotVaccin[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LotVaccinService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LotVaccin(0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureInventaire: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LotVaccin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureInventaire: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureInventaire: currentDate,
          },
          returnedFromService
        );

        service.create(new LotVaccin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LotVaccin', () => {
        const returnedFromService = Object.assign(
          {
            stockInitial: 1,
            stockActuel: 1,
            heureInventaire: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureInventaire: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LotVaccin', () => {
        const returnedFromService = Object.assign(
          {
            stockInitial: 1,
            stockActuel: 1,
            heureInventaire: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureInventaire: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LotVaccin', () => {
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
