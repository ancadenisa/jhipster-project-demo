import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Cherry } from './cherry.model';
import { CherryService } from './cherry.service';

@Component({
    selector: 'jhi-cherry-detail',
    templateUrl: './cherry-detail.component.html'
})
export class CherryDetailComponent implements OnInit, OnDestroy {

    cherry: Cherry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private cherryService: CherryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCherries();
    }

    load(id) {
        this.cherryService.find(id).subscribe((cherry) => {
            this.cherry = cherry;
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

    registerChangeInCherries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cherryListModification',
            (response) => this.load(this.cherry.id)
        );
    }
}
