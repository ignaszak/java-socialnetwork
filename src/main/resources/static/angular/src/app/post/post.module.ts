import {NgModule} from '@angular/core';

import {PostComponent} from './post.component';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MediaModule} from "../media/media.module";
import {NgxGalleryModule} from "ngx-gallery";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MediaModule,
        NgxGalleryModule
    ],
    declarations: [PostComponent]
})
export class PostModule {
}
