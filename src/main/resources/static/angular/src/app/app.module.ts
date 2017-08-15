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
import {LayoutComponent} from './layout/layout.component';
import {UserService} from "./user/user.service";
import {HttpModule} from "@angular/http";
import {UserServiceInterface} from "./user/user.service.interface";

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        BsDropdownModule.forRoot(),
        TabsModule.forRoot(),
        ChartsModule,
        HttpModule
    ],
    declarations: [
        AppComponent,
        LayoutComponent,
        NAV_DROPDOWN_DIRECTIVES,
        SIDEBAR_TOGGLE_DIRECTIVES,
        AsideToggleDirective
    ],
    bootstrap: [AppComponent],
    providers: [{provide: 'UserServiceInterface', useClass: UserService}]
})
export class AppModule {
}
