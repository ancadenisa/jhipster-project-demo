import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BerryComponent } from './berry.component';
import { BerryDetailComponent } from './berry-detail.component';
import { BerryPopupComponent } from './berry-dialog.component';
import { BerryDeletePopupComponent } from './berry-delete-dialog.component';

export const berryRoute: Routes = [
    {
        path: 'berry',
        component: BerryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.berry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'berry/:id',
        component: BerryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.berry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const berryPopupRoute: Routes = [
    {
        path: 'berry-new',
        component: BerryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.berry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'berry/:id/edit',
        component: BerryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.berry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'berry/:id/delete',
        component: BerryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'absenceManagementApp.berry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
