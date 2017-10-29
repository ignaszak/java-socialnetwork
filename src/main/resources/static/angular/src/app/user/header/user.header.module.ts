import {NgModule} from "@angular/core";
import {UserHeaderComponent} from "./user.header.component";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {ModalModule} from "ngx-bootstrap";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        ModalModule.forRoot()
    ],
    declarations: [UserHeaderComponent],
    exports: [UserHeaderComponent]
})
export class UserHeaderModule {

}
