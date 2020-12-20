import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CampagneService } from 'app/entities/campagne/campagne.service';
import { ICampagne, Campagne } from 'app/shared/model/campagne.model';

describe('Service Tests', () => {
  describe('Campagne Service', () => {
    let injector: TestBed;
    let service: CampagneService;
    let httpMock: HttpTestingController;
    let elemDefault: ICampagne;
    let expectedResult: ICampagne | ICampagne[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CampagneService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Campagne(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            debut: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Campagne', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            debut: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debut: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.create(new Campagne()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Campagne', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            description: 'BBBBBB',
            debut: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
            nbJoursRappel: 1,
            remarques: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debut: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Campagne', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            description: 'BBBBBB',
            debut: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
            nbJoursRappel: 1,
            remarques: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debut: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Campagne', () => {
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
