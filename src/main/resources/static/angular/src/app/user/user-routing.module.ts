import {NgModule} from '@angular/core';
import {
    Routes,
    RouterModule
} from '@angular/router';

import {UserComponent} from './user.component';
import {UserFriendsComponent} from "./friends/user.friends.component";

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
