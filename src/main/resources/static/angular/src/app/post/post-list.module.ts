import {NgModule} from "@angular/core";
import {PostComponent} from "./post.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {MediaModule} from "../media/media.module";
import {ImageUploadModule} from "angular2-image-upload";
import {NgxGalleryModule} from "ngx-gallery";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        MediaModule,
        ImageUploadModule.forRoot(),
        NgxGalleryModule
    ],
    declarations: [
        PostComponent
    ],
    exports: [
        PostComponent
    ]
})
export class PostListModule {
}
