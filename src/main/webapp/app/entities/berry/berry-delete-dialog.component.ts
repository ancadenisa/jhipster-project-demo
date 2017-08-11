import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Berry } from './berry.model';
import { BerryPopupService } from './berry-popup.service';
import { BerryService } from './berry.service';

@Component({
    selector: 'jhi-berry-delete-dialog',
    templateUrl: './berry-delete-dialog.component.html'
})
export class BerryDeleteDialogComponent {

    berry: Berry;

    constructor(
        private berryService: BerryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.berryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'berryListModification',
                content: 'Deleted an berry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-berry-delete-popup',
    template: ''
})
export class BerryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private berryPopupService: BerryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.berryPopupService
                .open(BerryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
