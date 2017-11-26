import {NgModule} from "@angular/core";
import {UserHeaderComponent} from "./user.header.component";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {ModalModule} from "ngx-bootstrap";
import {MediaModule} from "../../media/media.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        ModalModule.forRoot(),
        MediaModule
    ],
    declarations: [UserHeaderComponent],
    exports: [UserHeaderComponent]
})
export class UserHeaderModule {

}
