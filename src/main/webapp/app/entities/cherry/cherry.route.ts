import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CherryComponent } from './cherry.component';
import { CherryDetailComponent } from './cherry-detail.component';
import { CherryPopupComponent } from './cherry-dialog.component';
import { CherryDeletePopupComponent } from './cherry-delete-dialog.component';

export const cherryRoute: Routes = [
    {
        path: 'cherry',
        component: CherryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.cherry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cherry/:id',
        component: CherryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.cherry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cherryPopupRoute: Routes = [
    {
        path: 'cherry-new',
        component: CherryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.cherry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cherry/:id/edit',
        component: CherryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.cherry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cherry/:id/delete',
        component: CherryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.cherry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
