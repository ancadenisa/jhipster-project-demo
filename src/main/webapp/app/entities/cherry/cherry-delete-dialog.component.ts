import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cherry } from './cherry.model';
import { CherryPopupService } from './cherry-popup.service';
import { CherryService } from './cherry.service';

@Component({
    selector: 'jhi-cherry-delete-dialog',
    templateUrl: './cherry-delete-dialog.component.html'
})
export class CherryDeleteDialogComponent {

    cherry: Cherry;

    constructor(
        private cherryService: CherryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cherryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cherryListModification',
                content: 'Deleted an cherry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cherry-delete-popup',
    template: ''
})
export class CherryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cherryPopupService: CherryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cherryPopupService
                .open(CherryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
