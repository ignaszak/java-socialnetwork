import {NgModule} from '@angular/core';

import {PostComponent} from './post.component';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MediaModule} from "../shared/media/media.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MediaModule
    ],
    declarations: [PostComponent]
})
export class PostModule {
}
