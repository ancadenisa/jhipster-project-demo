import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Berry } from './berry.model';
import { BerryService } from './berry.service';

@Component({
    selector: 'jhi-berry-detail',
    templateUrl: './berry-detail.component.html'
})
export class BerryDetailComponent implements OnInit, OnDestroy {

    berry: Berry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private berryService: BerryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBerries();
    }

    load(id) {
        this.berryService.find(id).subscribe((berry) => {
            this.berry = berry;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBerries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'berryListModification',
            (response) => this.load(this.berry.id)
        );
    }
}
