import {NgModule} from '@angular/core';
import {ChartsModule} from 'ng2-charts/ng2-charts';

import {UserComponent} from './user.component';
import {UserRoutingModule} from './user-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {PostListModule} from "../post/post-list.module";

@NgModule({
    imports: [
        CommonModule,
        UserRoutingModule,
        ChartsModule,
        FormsModule,
        ReactiveFormsModule,
        PostListModule
    ],
    declarations: [
        UserComponent
    ]
})
export class UserModule {
}
