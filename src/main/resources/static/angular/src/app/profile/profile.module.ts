/**
 * Created by tomek on 01.07.17.
 */

import {NgModule} from "@angular/core";
import {ProfileRoutingModule} from "./profile-routing.module";
import {BsDropdownModule, TabsModule} from "ngx-bootstrap";
import {SettingsComponent} from "./settings/settings.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@NgModule({
    imports: [
        ProfileRoutingModule,
        BsDropdownModule.forRoot(),
        TabsModule,
        ReactiveFormsModule,
        CommonModule,
        FormsModule
    ],
    declarations: [
        SettingsComponent
    ]
})

export class ProfileModule {}
