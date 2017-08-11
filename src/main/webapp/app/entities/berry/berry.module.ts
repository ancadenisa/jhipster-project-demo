import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AbsenceManagementSharedModule } from '../../shared';
import {
    BerryService,
    BerryPopupService,
    BerryComponent,
    BerryDetailComponent,
    BerryDialogComponent,
    BerryPopupComponent,
    BerryDeletePopupComponent,
    BerryDeleteDialogComponent,
    berryRoute,
    berryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...berryRoute,
    ...berryPopupRoute,
];

@NgModule({
    imports: [
        AbsenceManagementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BerryComponent,
        BerryDetailComponent,
        BerryDialogComponent,
        BerryDeleteDialogComponent,
        BerryPopupComponent,
        BerryDeletePopupComponent,
    ],
    entryComponents: [
        BerryComponent,
        BerryDialogComponent,
        BerryPopupComponent,
        BerryDeleteDialogComponent,
        BerryDeletePopupComponent,
    ],
    providers: [
        BerryService,
        BerryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbsenceManagementBerryModule {}
