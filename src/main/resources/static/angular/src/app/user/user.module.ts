import {NgModule} from '@angular/core';
import {ChartsModule} from 'ng2-charts/ng2-charts';

import {UserComponent} from './user.component';
import {UserRoutingModule} from './user-routing.module';

@NgModule({
    imports: [
        UserRoutingModule,
        ChartsModule
    ],
    declarations: [UserComponent]
})
export class UserModule {
}
