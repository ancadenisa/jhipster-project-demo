/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AbsenceManagementTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CherryDetailComponent } from '../../../../../../main/webapp/app/entities/cherry/cherry-detail.component';
import { CherryService } from '../../../../../../main/webapp/app/entities/cherry/cherry.service';
import { Cherry } from '../../../../../../main/webapp/app/entities/cherry/cherry.model';

describe('Component Tests', () => {

    describe('Cherry Management Detail Component', () => {
        let comp: CherryDetailComponent;
        let fixture: ComponentFixture<CherryDetailComponent>;
        let service: CherryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AbsenceManagementTestModule],
                declarations: [CherryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CherryService,
                    JhiEventManager
                ]
            }).overrideTemplate(CherryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CherryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CherryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cherry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cherry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
