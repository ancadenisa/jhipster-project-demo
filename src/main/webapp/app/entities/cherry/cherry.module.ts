import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AbsenceManagementSharedModule } from '../../shared';
import {
    CherryService,
    CherryPopupService,
    CherryComponent,
    CherryDetailComponent,
    CherryDialogComponent,
    CherryPopupComponent,
    CherryDeletePopupComponent,
    CherryDeleteDialogComponent,
    cherryRoute,
    cherryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cherryRoute,
    ...cherryPopupRoute,
];

@NgModule({
    imports: [
        AbsenceManagementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CherryComponent,
        CherryDetailComponent,
        CherryDialogComponent,
        CherryDeleteDialogComponent,
        CherryPopupComponent,
        CherryDeletePopupComponent,
    ],
    entryComponents: [
        CherryComponent,
        CherryDialogComponent,
        CherryPopupComponent,
        CherryDeleteDialogComponent,
        CherryDeletePopupComponent,
    ],
    providers: [
        CherryService,
        CherryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbsenceManagementCherryModule {}
