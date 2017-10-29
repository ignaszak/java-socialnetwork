import {Component, Inject, OnInit} from '@angular/core';
import {User} from "./user";
import {ActivatedRoute, Params}   from '@angular/router';

import 'rxjs/add/operator/switchMap';
import {UserServiceInterface} from "./user.service.interface";

@Component({
    templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {

    user: User;

    constructor(
        private route: ActivatedRoute,
        @Inject('UserServiceInterface') private userService: UserServiceInterface
    ) {
        this.user = new User();
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.userService.getUserByUsername(params['username']))
            .subscribe(user => {
                console.log('Parent: ',user);
                this.user = user
            });
    }
}
