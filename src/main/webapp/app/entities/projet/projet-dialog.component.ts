import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Projet } from './projet.model';
import { ProjetPopupService } from './projet-popup.service';
import { ProjetService } from './projet.service';

@Component({
    selector: 'jhi-projet-dialog',
    templateUrl: './projet-dialog.component.html'
})
export class ProjetDialogComponent implements OnInit {

    projet: Projet;
    isSaving: boolean;
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private projetService: ProjetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projetService.update(this.projet));
        } else {
            this.subscribeToSaveResponse(
                this.projetService.create(this.projet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Projet>>) {
        result.subscribe((res: HttpResponse<Projet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Projet) {
        this.eventManager.broadcast({ name: 'projetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-projet-popup',
    template: ''
})
export class ProjetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projetPopupService: ProjetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component, params['id']);
            } else {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
