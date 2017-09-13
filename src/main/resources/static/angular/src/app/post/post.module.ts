import {NgModule} from '@angular/core';

import {PostComponent} from './post.component';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [PostComponent]
})
export class PostModule {
}
