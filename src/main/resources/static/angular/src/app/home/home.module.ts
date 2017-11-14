import {NgModule} from '@angular/core';

import {HomeComponent} from './home.component';
import {HomeRoutingModule} from './home-routing.module';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PostModule} from "../post/post.module";
import {MediaModule} from "../shared/media/media.module";

@NgModule({
    imports: [
        CommonModule,
        HomeRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        PostModule,
        MediaModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {
}
