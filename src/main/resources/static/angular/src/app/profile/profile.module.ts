/**
 * Created by tomek on 01.07.17.
 */

import {NgModule} from "@angular/core";
import {ProfileRoutingModule} from "./profile-routing.module";
import {BsDropdownModule, TabsModule} from "ngx-bootstrap";
import {SettingsComponent} from "./settings/settings.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {UserHeaderModule} from "../user/header/user.header.module";
import {ImageUploadModule} from "angular2-image-upload";
import {MediaModule} from "../media/media.module";

@NgModule({
    imports: [
        ProfileRoutingModule,
        BsDropdownModule.forRoot(),
        TabsModule,
        ReactiveFormsModule,
        CommonModule,
        FormsModule,
        UserHeaderModule,
        ImageUploadModule.forRoot(),
        MediaModule
    ],
    declarations: [
        SettingsComponent
    ]
})

export class ProfileModule {}
