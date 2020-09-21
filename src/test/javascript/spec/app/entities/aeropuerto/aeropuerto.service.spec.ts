import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AeropuertoService } from 'app/entities/aeropuerto/aeropuerto.service';
import { IAeropuerto, Aeropuerto } from 'app/shared/model/aeropuerto.model';

describe('Service Tests', () => {
  describe('Aeropuerto Service', () => {
    let injector: TestBed;
    let service: AeropuertoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAeropuerto;
    let expectedResult: IAeropuerto | IAeropuerto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AeropuertoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Aeropuerto(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Aeropuerto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            idaero: 1,
            nombre: 'BBBBBB',
            pais: 'BBBBBB',
            ciudad: 'BBBBBB',
            direccion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            idaero: 1,
            nombre: 'BBBBBB',
            pais: 'BBBBBB',
            ciudad: 'BBBBBB',
            direccion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Aeropuerto', () => {
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
