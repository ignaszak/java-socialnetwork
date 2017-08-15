import {Component, Inject, OnInit, Output} from '@angular/core';
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";

@Component({
    selector: 'app-dashboard',
    templateUrl: './layout.component.html'
})
export class LayoutComponent implements OnInit {

    public disabled = false;
    public status: { isopen: boolean } = {isopen: false};

    @Output() currentUser: User;

    constructor(@Inject('UserServiceInterface') private userService: UserServiceInterface) {
        this.currentUser = new User();
    }

    ngOnInit(): void {
        this.getCurrentUser();
    }

    public toggled(open: boolean): void {
        console.log('Dropdown is now: ', open);
    }

    public toggleDropdown($event: MouseEvent): void {
        $event.preventDefault();
        $event.stopPropagation();
        this.status.isopen = !this.status.isopen;
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }
}
