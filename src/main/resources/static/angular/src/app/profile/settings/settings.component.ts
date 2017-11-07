/**
 * Created by tomek on 29.06.17.
 */

import {Component, Inject, OnInit} from "@angular/core";
import {User} from "../../user/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Http} from "@angular/http";
import {UserServiceInterface} from "../../user/user.service.interface";
import {UserService} from "../../user/user.service";
import {RestProvider} from "../../rest/rest-provider";
import {FileHolder} from "angular2-image-upload";
import {Events} from "../../shared/event/events";
import {Event} from "../../shared/event/event";

@Component({
    templateUrl: './settings.component.html'
})
export class SettingsComponent extends Event implements OnInit{

    currentUser: User;
    generalForm: FormGroup;
    generalFormSubmit: boolean;
    generalFormEmailActivation: boolean;
    profilePhotoUrl: string = RestProvider.USER_CURRENT_MEDIAS_PROFILE;

    constructor(
        @Inject('UserServiceInterface') private userService: UserServiceInterface,
        private http: Http
    ) {
        super();
        this.generalFormSubmit = false;
        this.generalFormEmailActivation = false;
        this.currentUser = new User();
        this.generalForm = new FormGroup({
            email: new FormControl(),
            caption: new FormControl()
        });
    }

    ngOnInit(): void {
        this.getCurrentUserAndInitForms();
    }

    private getCurrentUserAndInitForms(): void {
        this.userService.getCurrentUser().then(user => {
            this.currentUser = user;
            this.initForms(user);
        });
    }

    private initForms(user: User): void {
        this.generalForm = new FormGroup({
            email: new FormControl(
                user.email,
                [
                    Validators.required,
                    Validators.pattern("^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$")
                ],
                [UserService.uniqueEmail(this.http, user.email)]
            ),
            caption: new FormControl(user.caption)
        });
    }

    onSubmitGeneral(): void {
        if (this.generalForm.valid) {
            let user: User = this.generalForm.value;
            user.id = this.currentUser.id;
            this.userService.updateUser(user);
            this.generalFormSubmit = true;
            if (this.generalForm.controls.email.valid && this.generalForm.controls.email.dirty) {
                this.generalFormEmailActivation = true;
            }
            console.log(user);
        }
    }

    onUploadFinished(event: FileHolder): void {
        this.userService.getCurrentUser().then(user => {
            this.currentUser = user;
            this.sendEvent(Events.UPDATE_CURRENT_USER, user);
        });
    }

    onUploadStateChanged(event: any): void {
    }
}
