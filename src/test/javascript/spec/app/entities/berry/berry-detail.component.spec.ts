/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AbsenceManagementTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BerryDetailComponent } from '../../../../../../main/webapp/app/entities/berry/berry-detail.component';
import { BerryService } from '../../../../../../main/webapp/app/entities/berry/berry.service';
import { Berry } from '../../../../../../main/webapp/app/entities/berry/berry.model';

describe('Component Tests', () => {

    describe('Berry Management Detail Component', () => {
        let comp: BerryDetailComponent;
        let fixture: ComponentFixture<BerryDetailComponent>;
        let service: BerryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AbsenceManagementTestModule],
                declarations: [BerryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BerryService,
                    JhiEventManager
                ]
            }).overrideTemplate(BerryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BerryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BerryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Berry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.berry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
