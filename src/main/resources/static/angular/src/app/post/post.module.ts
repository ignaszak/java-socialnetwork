import {NgModule} from "@angular/core";
import {PostComponent} from "./post.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MediaModule} from "../media/media.module";
import {NgxGalleryModule} from "ngx-gallery";
import {RouterModule} from "@angular/router";
import {ImageUploadModule} from "angular2-image-upload";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MediaModule,
        NgxGalleryModule,
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
