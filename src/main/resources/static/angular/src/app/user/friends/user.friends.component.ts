import {Component, HostListener, Inject, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {User} from "../user";
import {UserServiceInterface} from "../user.service.interface";
import {ActivatedRoute, Params} from "@angular/router";
import {Invitation} from "../invitation";

@Component({
    templateUrl: './user.friends.component.html'
})
export class UserFriendsComponent implements OnInit, OnChanges {

    user: User;
    friends: User[] = null;
    private nextFriendsPage: number = 0;
    private hasNextFriendsPage: boolean = true;

    constructor(
        private route: ActivatedRoute,
        @Inject('UserServiceInterface') private userService: UserServiceInterface
    ) {
        this.user = new User();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.user = changes.user.currentValue;
        this.friends = null;
        this.nextFriendsPage = 0;
        this.hasNextFriendsPage = true;
        this.getFriends();
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.userService.getUserByUsername(params['username']))
            .subscribe(user => {
                this.user = user;
                this.getFriends();
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScrollDown(): void {
        if (window.innerHeight + window.scrollY === document.body.scrollHeight) this.getFriends();
    }

    private getFriends(): void {
        if (this.hasNextFriendsPage) {
            this.userService.getFriendsByUser(this.user, this.nextFriendsPage).then(response => {
                let friends: User[] = response.getResponse();
                if (this.friends === null) {
                    this.friends = friends;
                } else {
                    for (let friend of friends) this.friends.push(friend);
                }
                this.nextFriendsPage = response.getNextPage();
                this.hasNextFriendsPage = response.hasNextPage();
            })
        }
    }

}
