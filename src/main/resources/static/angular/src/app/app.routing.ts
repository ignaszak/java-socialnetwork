import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

// Layouts
import {IndexComponent} from './index/index.component';
import {HomeComponent} from "./home/home.component";

export const routes: Routes = [
    {
        path: '',
        component: IndexComponent,
        data: {
            title: 'Home'
        },
        children: [
            {
                path: '',
                component: HomeComponent
            },
            {
                path: ':username',
                loadChildren: './user/user.module#UserModule'
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
