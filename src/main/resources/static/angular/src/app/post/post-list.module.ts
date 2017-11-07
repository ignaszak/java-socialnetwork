import {NgModule} from "@angular/core";
import {PostComponent} from "./post.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {MediaModule} from "../shared/media/media.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        MediaModule
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
