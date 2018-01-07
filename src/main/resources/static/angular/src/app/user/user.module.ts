import {NgModule} from '@angular/core';
import {ChartsModule} from 'ng2-charts/ng2-charts';

import {UserComponent} from './user.component';
import {UserRoutingModule} from './user-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {PostModule} from "../post/post.module";
import {UserHeaderModule} from "./header/user.header.module";
import {UserFriendsComponent} from "./friends/user.friends.component";
import {MediaModule} from "../media/media.module";
import {UserSettingsComponent} from "./settings/user.settings.component";
import {TabsModule} from "ngx-bootstrap";
import {ImageUploadModule} from "angular2-image-upload";

@NgModule({
    imports: [
        CommonModule,
        UserRoutingModule,
        ChartsModule,
        FormsModule,
        ReactiveFormsModule,
        PostModule,
        UserHeaderModule,
        MediaModule,
        TabsModule,
        ImageUploadModule.forRoot(),
    ],
    declarations: [
        UserComponent,
        UserFriendsComponent,
        UserSettingsComponent
    ]
})
export class UserModule {
}
