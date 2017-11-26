import {NgModule} from '@angular/core';
import {ChartsModule} from 'ng2-charts/ng2-charts';

import {UserComponent} from './user.component';
import {UserRoutingModule} from './user-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {PostListModule} from "../post/post-list.module";
import {UserHeaderModule} from "./header/user.header.module";
import {UserFriendsComponent} from "./friends/user.friends.component";
import {MediaModule} from "../media/media.module";

@NgModule({
    imports: [
        CommonModule,
        UserRoutingModule,
        ChartsModule,
        FormsModule,
        ReactiveFormsModule,
        PostListModule,
        UserHeaderModule,
        MediaModule
    ],
    declarations: [
        UserComponent,
        UserFriendsComponent,
    ]
})
export class UserModule {
}
