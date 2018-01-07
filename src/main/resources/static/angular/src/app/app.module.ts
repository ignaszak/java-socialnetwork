import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {TabsModule} from 'ngx-bootstrap/tabs';
import {NAV_DROPDOWN_DIRECTIVES} from './shared/nav-dropdown.directive';

import {ChartsModule} from 'ng2-charts/ng2-charts';
import {SIDEBAR_TOGGLE_DIRECTIVES} from './shared/sidebar.directive';
import {AsideToggleDirective} from './shared/aside.directive';

// Routing Module
import {AppRoutingModule} from './app.routing';

// Layouts
import {IndexComponent} from './index/index.component';
import {UserService} from "./user/user.service";
import {HttpModule} from "@angular/http";
import {UserServiceInterface} from "./user/user.service.interface";
import {PostService} from "./post/post.service";
import {RestProvider} from "./rest/rest-provider";
import {CommentService} from "./comment/comment.service";
import {MediaModule} from "./media/media.module";
import {MediaService} from "./media/media.service";
import {NgxGalleryModule} from "ngx-gallery";
import {HomeModule} from "./home/home.module";

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        BsDropdownModule.forRoot(),
        TabsModule.forRoot(),
        ChartsModule,
        HttpModule,
        MediaModule,
        NgxGalleryModule,
        HomeModule
    ],
    declarations: [
        AppComponent,
        IndexComponent,
        NAV_DROPDOWN_DIRECTIVES,
        SIDEBAR_TOGGLE_DIRECTIVES,
        AsideToggleDirective
    ],
    bootstrap: [AppComponent],
    providers: [
        {provide: 'RestProviderInterface', useClass: RestProvider},
        {provide: 'UserServiceInterface', useClass: UserService},
        {provide: 'PostServiceInterface', useClass: PostService},
        {provide: 'CommentServiceInterface', useClass: CommentService},
        {provide: 'MediaServiceInterface', useClass: MediaService}
    ]
})
export class AppModule {
}
