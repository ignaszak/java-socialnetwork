import {NgModule} from '@angular/core';

import {HomeComponent} from './home.component';
import {HomeRoutingModule} from './home-routing.module';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PostListModule} from "../post/post-list.module";
import {MediaModule} from "../media/media.module";

@NgModule({
    imports: [
        CommonModule,
        HomeRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        PostListModule,
        MediaModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {
}
