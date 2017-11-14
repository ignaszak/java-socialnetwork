import {NgModule} from "@angular/core";
import {PostComponent} from "./post.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {MediaModule} from "../shared/media/media.module";
import {ImageUploadModule} from "angular2-image-upload";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        MediaModule,
        ImageUploadModule.forRoot()
    ],
    declarations: [
        PostComponent
    ],
    exports: [
        PostComponent
    ]
})
export class PostModule {
}
