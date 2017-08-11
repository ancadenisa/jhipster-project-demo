import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AbsenceManagementBlogModule } from './blog/blog.module';
import { AbsenceManagementEntryModule } from './entry/entry.module';
import { AbsenceManagementBerryModule } from './berry/berry.module';
import { AbsenceManagementCherryModule } from './cherry/cherry.module';
import { AbsenceManagementCategoryModule } from './category/category.module';
import { AbsenceManagementTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AbsenceManagementBlogModule,
        AbsenceManagementEntryModule,
        AbsenceManagementBerryModule,
        AbsenceManagementCherryModule,
        AbsenceManagementCategoryModule,
        AbsenceManagementTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbsenceManagementEntityModule {}
