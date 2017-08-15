import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";

import {SettingsComponent} from "./settings/settings.component";

const ROUTES: Routes = [
    {
        path: '',
        children: [
            {
                path: 'settings',
                component: SettingsComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(ROUTES)],
    exports: [RouterModule]
})

export class ProfileRoutingModule {}
