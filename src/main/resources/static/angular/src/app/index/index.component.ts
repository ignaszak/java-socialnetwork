import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";
import {MessageInterface} from "../shared/event/message.interface";
import {Events} from "../shared/event/events";
import {Event} from "../shared/event/event";

@Component({
    selector: 'app-dashboard',
    templateUrl: './index.component.html'
})
export class IndexComponent extends Event implements OnInit {

    public disabled = false;
    public status: { isopen: boolean } = {isopen: false};
    currentUser: User;

    constructor(@Inject('UserServiceInterface') private userService: UserServiceInterface) {
        super();
        this.currentUser = new User();
        this.addEvent(Events.UPDATE_CURRENT_USER, (message: MessageInterface<User>) => {
            this.currentUser = message.getMessage();
        });
        this.listenToEvents();
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
