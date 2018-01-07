import {NgModule} from '@angular/core';
import {
    Routes,
    RouterModule
} from '@angular/router';

import {UserComponent} from './user.component';
import {UserFriendsComponent} from "./friends/user.friends.component";
import {UserSettingsComponent} from "./settings/user.settings.component";

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                component: UserComponent
            },
            {
                path: 'friends',
                component: UserFriendsComponent
            },
            {
                path: 'settings',
                component: UserSettingsComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserRoutingModule {
}
