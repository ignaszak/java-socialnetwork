import {NgModule} from '@angular/core';

import {HomeComponent} from './home.component';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PostModule} from "../post/post.module";
import {MediaModule} from "../media/media.module";

@NgModule({
    imports: [
        CommonModule,
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
