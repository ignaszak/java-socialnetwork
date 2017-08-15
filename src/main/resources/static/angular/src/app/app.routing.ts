import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

// Layouts
import {LayoutComponent} from './layout/layout.component';

export const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        data: {
            title: 'Home'
        },
        children: [
            {
                path: '',
                loadChildren: './home/home.module#HomeModule'
            },
            {
                path: ':username',
                loadChildren: './user/user.module#UserModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#ProfileModule'
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
