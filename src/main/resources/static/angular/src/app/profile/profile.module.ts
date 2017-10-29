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

@NgModule({
    imports: [
        ProfileRoutingModule,
        BsDropdownModule.forRoot(),
        TabsModule,
        ReactiveFormsModule,
        CommonModule,
        FormsModule,
        UserHeaderModule
    ],
    declarations: [
        SettingsComponent
    ]
})

export class ProfileModule {}
