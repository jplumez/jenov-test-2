import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { QuestionnaireComponent } from 'app/entities/questionnaire/questionnaire.component';
import { QuestionnaireService } from 'app/entities/questionnaire/questionnaire.service';
import { Questionnaire } from 'app/shared/model/questionnaire.model';

describe('Component Tests', () => {
  describe('Questionnaire Management Component', () => {
    let comp: QuestionnaireComponent;
    let fixture: ComponentFixture<QuestionnaireComponent>;
    let service: QuestionnaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [QuestionnaireComponent],
      })
        .overrideTemplate(QuestionnaireComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionnaireComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionnaireService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Questionnaire(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionnaires && comp.questionnaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
